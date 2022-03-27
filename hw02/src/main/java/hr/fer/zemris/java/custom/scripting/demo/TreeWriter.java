package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.node.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class TreeWriter extends BaseDemo {
    private static class WriterVisitor implements INodeVisitor {
        @Override
        public void visitTextNode(TextNode node) {
            System.out.print(node.toCode());
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            String step = node.getStepExpression() == null ? "" : node.getStepExpression().asText();
            System.out.printf("{$ FOR %s %s %s %s $}", node.getVariable().asText(), node.getStartExpression().asText(), node.getEndExpression().asText(), step);

            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }

            System.out.println("{$ END $}");
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            // Kinda cheating here since this method is available in all nodes and I should reimplement it here like in all nodes
            System.out.print(node.toCode());
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }
        }
    }

    public static void main(String[] args) {
        new TreeWriter().run(args);
    }

    public void run(String[] args) {
        String code = this.getContent(args);
        SmartScriptParser p = new SmartScriptParser(code);

        WriterVisitor visitor = new WriterVisitor();
        p.getDocumentNode().accept(visitor);
    }
}
