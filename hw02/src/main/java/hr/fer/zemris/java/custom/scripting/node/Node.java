package hr.fer.zemris.java.custom.scripting.node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Base class used to represent all Nodes
 *
 * @author franzekan
 */
public abstract class Node {
    /**
     * Node children
     */
    private final List<Node> children;

    /**
     * Instantiates a new Node.
     */
    public Node() {
        this.children = new ArrayList<>();
    }

    /**
     * Add child node.
     *
     * @param child the child
     */
    public void addChildNode(Node child) {
        this.children.add(child);
    }

    /**
     * Number of children
     *
     * @return the number of children
     */
    public int numberOfChildren() {
        return this.children.size();
    }

    /**
     * Gets child by index
     *
     * @param index the index
     * @return the child
     */
    public Node getChild(int index) {
        return this.children.get(index);
    }

    public abstract void accept(INodeVisitor visitor);

    /**
     * To code string.
     *
     * @return the string
     */
    public abstract String toCode();

    /**
     * To structure string.
     *
     * @param depth the depth
     * @return the string
     */
    public String toStructure(int depth) {
        return "";
    }

    /**
     * Helper for used to convert all the children to string
     *
     * @return the string
     */
    protected String childrenToString() {
        StringBuilder sb = new StringBuilder();
        this.forEachChildren(value -> sb.append(value.toCode()));

        return sb.toString();
    }

    /**
     * For each over all children
     *
     * @param p Processor instance
     */
    public void forEachChildren(Consumer<Node> p) {
        this.children.forEach(p);
    }
}
