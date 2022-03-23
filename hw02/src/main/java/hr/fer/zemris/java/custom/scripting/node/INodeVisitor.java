package hr.fer.zemris.java.custom.scripting.node;

public interface INodeVisitor {
    public void visitTextNode(TextNode node);
    public void visitForLoopNode(ForLoopNode node);
    public void visitEchoNode(EchoNode node);
    public void visitDocumentNode(DocumentNode node);
}
