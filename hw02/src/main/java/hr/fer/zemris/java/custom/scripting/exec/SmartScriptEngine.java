package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.exec.Functions.FunctionContext;
import hr.fer.zemris.java.custom.scripting.exec.Functions.FunctionRunner;
import hr.fer.zemris.java.custom.scripting.exec.Util.TypeCast;
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
            Stack<Object> tmpStack = new Stack<>();

            Element[] elements = node.getElements();
            for (Element element : elements) {
                switch (element) {
                    case ElementConstantInteger elem -> tmpStack.push(elem.getValue());
                    case ElementConstantDouble elem -> tmpStack.push(elem.getValue());
                    case ElementString elem -> tmpStack.push(elem.getValue());
                    case ElementVariable elem -> {
                        Optional<ValueWrapper> var = stack.stream()
                                .filter(v -> v.name().equals(elem.getName()))
                                .findFirst();

                        if (var.isEmpty()) {
                            throw new RuntimeException("Variable " + elem.getName() + " not defined.");
                        }

                        tmpStack.push(var.get().value());
                    }
                    case ElementOperator elem -> {
                        if (tmpStack.size() < 2) {
                            throw new RuntimeException("Not enough arguments for operator " + elem.getSymbol());
                        }

                        Double a = TypeCast.toDouble(tmpStack.pop(), "First argument isn't a number");
                        Double b = TypeCast.toDouble(tmpStack.pop(), "Second argument isn't a number");

                        switch (elem.getSymbol()) {
                            case "+" -> tmpStack.push(a + b);
                            case "-" -> tmpStack.push(a - b);
                            case "/" -> tmpStack.push(a / b);
                            case "*" -> tmpStack.push(a * b);
                            default -> throw new RuntimeException("Invalid operator " + elem.getSymbol());
                        }
                    }
                    case ElementFunction elem -> FunctionRunner.run(elem.getName(), new FunctionContext(tmpStack, requestContext));
                    default -> throw new IllegalStateException("Unexpected value: " + element);
                }
            }

            for (Object item : tmpStack) {
                try {
                    if (item instanceof Double) {
                        item = ((Double) item).intValue();
                    }
                    requestContext.write(item.toString());
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
