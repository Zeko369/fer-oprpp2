package hr.fer.zemris.java.custom.scripting;

import hr.fer.zemris.java.custom.scripting.node.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class Main {
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
        String docBody = "Sample text\n" +
                "{$ FOR i 1 10 1 $}\n" +
                "  This is {$= i $}-th time this message is generated.\n" +
                "{$   END $}\n" +
                "{$FOR i 0 10 2 $}\n" +
                "  sin({$=i$}^2) = {$= i i * @sin  \"0.000\" @decfmt $}\n" +
                "{$END$}\n";

        SmartScriptParser p = new SmartScriptParser(docBody);
        WriterVisitor visitor = new WriterVisitor();
        p.getDocumentNode().accept(visitor);
    }
}
