package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.exec.Functions.FunctionContext;
import hr.fer.zemris.java.custom.scripting.exec.Functions.FunctionRunner;
import hr.fer.zemris.java.custom.scripting.node.*;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.*;

/**
 * The type Smart script engine.
 *
 * @author franzekan
 */
public class SmartScriptEngine {
    private final DocumentNode documentNode;
    private final RequestContext requestContext;
    private final ObjectMultistack stack = new ObjectMultistack();

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
            String key = node.getVariable().getName();
            String endValue = node.getEndExpression().asText();
            // Hack: this should be number, but if it starts with a v then it is a variable
            if (endValue.startsWith("v")) {
                endValue = endValue.substring(1);
                endValue = stack.peek(endValue).getValue().toString();
            }

            stack.push(key, new ValueWrapper(node.getStartExpression().asText()));
            while (stack.peek(key).numCompare(endValue) < 1) {
                for (int i = 0; i < node.numberOfChildren(); i++) {
                    node.getChild(i).accept(this);
                }

                stack.peek(key).add(node.getStepExpression().asText());
            }
            stack.pop(key);
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            Stack<ValueWrapper> tmpStack = new Stack<>();

            Element[] elements = node.getElements();
            for (Element element : elements) {
                switch (element) {
                    case ElementConstantInteger elem -> tmpStack.push(new ValueWrapper(elem.getValue()));
                    case ElementConstantDouble elem -> tmpStack.push(new ValueWrapper(elem.getValue()));
                    case ElementString elem -> tmpStack.push(new ValueWrapper(elem.getValue()));
                    case ElementVariable elem -> {
                        ValueWrapper val = stack.peek(elem.getName());
                        if (val == null) {
                            throw new RuntimeException("Variable " + elem.getName() + " not defined.");
                        }

                        tmpStack.push(val);
                    }
                    case ElementOperator elem -> {
                        if (tmpStack.size() < 2) {
                            throw new RuntimeException("Not enough arguments for operator " + elem.getSymbol());
                        }

                        ValueWrapper a = ValueWrapper.copy(tmpStack.pop());
                        ValueWrapper b = tmpStack.pop();

                        switch (elem.getSymbol()) {
                            case "+" -> a.add(b.getValue());
                            case "-" -> a.subtract(b.getValue());
                            case "/" -> a.divide(b.getValue());
                            case "*" -> a.multiply(b.getValue());
                            default -> throw new RuntimeException("Invalid operator " + elem.getSymbol());
                        }

                        tmpStack.push(a);
                    }
                    case ElementFunction elem -> FunctionRunner.run(elem.getName(), new FunctionContext(tmpStack, requestContext));
                    default -> throw new IllegalStateException("Unexpected value: " + element);
                }
            }

            for (ValueWrapper item : tmpStack) {
                try {
                    requestContext.write(item.getValue().toString());
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

    /**
     * Instantiates a new Smart script engine.
     *
     * @param documentNode   the document node
     * @param requestContext the request context
     */
    public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        this.documentNode = documentNode;
        this.requestContext = requestContext;
        this.requestContext.getTemporaryParameterNames().forEach((k) -> stack.push(k, new ValueWrapper(this.requestContext.getTemporaryParameter(k))));
    }

    /**
     * Execute.
     */
    public void execute() {
        this.documentNode.accept(this.visitor);
    }
}
