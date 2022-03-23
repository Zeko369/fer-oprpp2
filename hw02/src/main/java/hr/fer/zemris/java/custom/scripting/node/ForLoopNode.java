package hr.fer.zemris.java.custom.scripting.node;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class used to represent the ForLoopNode
 * <code>{$ FOR i 0 10 1 $}</code>
 *
 * @author franzekan
 */
public class ForLoopNode extends Node {
    private final ElementVariable variable;
    private final Element startExpression;
    private final Element endExpression;
    private final Element stepExpression;

    /**
     * Instantiates a new For loop node.
     *
     * @param variable        the variable
     * @param startExpression the start expression
     * @param endExpression   the end expression
     * @param stepExpression  the step expression
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * Gets variable.
     *
     * @return the variable
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Gets start expression.
     *
     * @return the start expression
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Gets end expression.
     *
     * @return the end expression
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Gets step expression.
     *
     * @return the step expression
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    @Override
    public String toCode() {
        StringBuilder sb = new StringBuilder();
        String step = this.stepExpression == null ? "" : this.stepExpression.asText();

        sb.append(String.format("{$ FOR %s %s %s %s $}", this.variable.asText(), this.startExpression.asText(), this.endExpression.asText(), step));
        sb.append(this.childrenToString());
        sb.append("{$ END $}");

        return sb.toString();
    }

    @Override
    public String toStructure(int depth) {
        StringBuilder sb = new StringBuilder();
        String step = this.stepExpression == null ? "" : this.stepExpression.asText();

        sb.append(String.format("%sFOR: %s %s %s %s\n", " ".repeat(depth), this.variable.asText(), this.startExpression.asText(), this.endExpression.asText(), step));
        this.forEachChildren(c -> sb.append(String.format("%s%n", c.toStructure(depth + 2))));

        return sb.toString();
    }
}
