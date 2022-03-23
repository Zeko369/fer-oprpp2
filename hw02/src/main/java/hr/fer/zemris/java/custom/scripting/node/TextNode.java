package hr.fer.zemris.java.custom.scripting.node;

/**
 * Class used to represent the TextNode
 * <code>Foo Bar Text</code>
 *
 * @author franzekan
 */
public class TextNode extends Node {
    private final String text;

    /**
     * Instantiates a new Text node.
     *
     * @param text the text
     */
    public TextNode(String text) {
        this.text = text;
    }

    @Override
    public String toCode() {
        return this.text;
    }

    @Override
    public String toStructure(int depth) {
        return String.format("%sTEXT: %s", " ".repeat(depth), this.text.replace("\n", "\\n"));
    }
}
