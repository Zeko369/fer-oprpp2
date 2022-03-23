package hr.fer.zemris.java.custom.scripting.node;

/**
 * Root node in the document
 *
 * @author franzekan
 */
public class DocumentNode extends Node {
    @Override
    public String toString() {
        return this.toCode();
    }

    @Override
    public String toCode() {
        return this.childrenToString();
    }

    /**
     * Returns the structure of the tree bellow
     *
     * @return the string
     */
    public String toStructure() {
        StringBuilder sb = new StringBuilder();
        sb.append("ROOT:\n");
        this.forEachChildren((c) -> {
            sb.append(c.toStructure(2));
            sb.append("\n");
        });

        return sb.toString();
    }

    /**
     * Depth is ignored in DocumentNode
     */
    @Override
    public String toStructure(int depth) {
        return this.toStructure();
    }
}
