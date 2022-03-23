package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element class used to store an operator <code>+/-*^</code>
 * I.e. <code>+</code> will be parsed into this class
 *
 * @author franzekan
 */
public class ElementOperator extends Element {
    private final String symbol;

    /**
     * Instantiates a new Element operator.
     *
     * @param symbol the symbol
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Symbol getter
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String asText() {
        return this.symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementOperator that = (ElementOperator) o;

        if (this.symbol != null) {
            return this.symbol.equals(that.symbol);
        }

        return that.symbol == null;
    }
}
