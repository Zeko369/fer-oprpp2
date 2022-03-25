package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.exec.Functions.FunctionContext;
import hr.fer.zemris.java.custom.scripting.exec.Functions.FunctionRunner;
import hr.fer.zemris.java.custom.scripting.node.*;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.*;

public class SmartScriptEngine {
    private final DocumentNode documentNode;
    private final RequestContext requestContext;
    private final Stack<ValueWrapper> stack = new Stack<>();

    private final INodeVisitor visitor = new INodeVisitor() {
        @Override
        public void visitTextNode(TextNode node) {
            try {
                requestContext.write(node.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            stack.push(new ValueWrapper(node.getStartExpression().asInt(), node.getVariable().asText()));
            while (stack.peek().asInt() < node.getEndExpression().asInt()) {
                for (int i = 0; i < node.numberOfChildren(); i++) {
                    node.getChild(i).accept(this);
                }

                ValueWrapper val = stack.pop();
                stack.push(new ValueWrapper(val.asInt() + node.getStepExpression().asInt(), val.name()));
            }
            stack.pop();
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            Stack<Object> tmp = new Stack<>();

            Element[] elements = node.getElements();
            for (Element element : elements) {
                switch (element) {
                    case ElementConstantInteger elem -> tmp.push(elem.getValue());
                    case ElementConstantDouble elem -> tmp.push(elem.getValue());
                    case ElementString elem -> tmp.push(elem.getValue());
                    case ElementVariable elem -> {
                        Optional<ValueWrapper> var = stack.stream()
                                .filter(v -> v.name().equals(elem.getName()))
                                .findFirst();

                        if (var.isEmpty()) {
                            throw new RuntimeException("Variable " + elem.getName() + " not defined.");
                        }

                        tmp.push(var.get().value());
                    }
                    case ElementOperator elem -> {
                        if (tmp.size() < 2) {
                            throw new RuntimeException("Not enough arguments for operator " + elem.getSymbol());
                        }

                        Object a = tmp.pop();
                        Object b = tmp.pop();

                        Double aDouble;
                        Double bDouble;

                        if (a instanceof Integer) {
                            aDouble = (double) (Integer) a;
                        } else if (a instanceof Double) {
                            aDouble = (Double) a;
                        } else {
                            throw new RuntimeException("Invalid type of first argument for operator " + elem.getSymbol());
                        }

                        if (b instanceof Integer) {
                            bDouble = (double) (Integer) b;
                        } else if (b instanceof Double) {
                            bDouble = (Double) b;
                        } else {
                            throw new RuntimeException("Invalid type of second argument for operator " + elem.getSymbol());
                        }

                        switch (elem.getSymbol()) {
                            case "+" -> tmp.push(aDouble + bDouble);
                            case "-" -> tmp.push(aDouble - bDouble);
                            case "/" -> tmp.push(aDouble / bDouble);
                            case "*" -> tmp.push(aDouble * bDouble);
                            default -> throw new RuntimeException("Invalid operator " + elem.getSymbol());
                        }
                    }
                    case ElementFunction elem -> FunctionRunner.run(elem.getName(), new FunctionContext(tmp, requestContext));
                    default -> throw new IllegalStateException("Unexpected value: " + element);
                }
            }

            while (!tmp.isEmpty()) {
                try {
                    requestContext.write(tmp.pop().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }
        }
    };

    public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        this.documentNode = documentNode;
        this.requestContext = requestContext;
    }

    public void execute() {
        this.documentNode.accept(this.visitor);
    }
}
