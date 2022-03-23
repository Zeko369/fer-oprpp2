package hr.fer.zemris.java.custom.scripting.node;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class used to represent the EchoNode
 * <code>{$= 123 $}</code>
 *
 * @author franzekan
 */
public class EchoNode extends Node {
    private final Element[] elements;

    /**
     * Instantiates a new Echo node.
     *
     * @param elements the elements
     */
    public EchoNode(Element[] elements) {
        this.elements = elements;
    }

    /**
     * Elements getter
     *
     * @return the elements
     */
    public Element[] getElements() {
        return elements;
    }

    private String arguments() {
        StringBuilder sb = new StringBuilder();

        for (Element element : this.elements) {
            sb.append(element.asText());
            sb.append(" ");
        }

        return sb.toString();
    }

    @Override
    public String toCode() {
        return String.format("{$= %s $}", this.arguments());
    }

    @Override
    public String toStructure(int depth) {
        return String.format("%sECHO: %s", " ".repeat(depth), this.arguments().replace("\n", "\\n"));
    }
}
