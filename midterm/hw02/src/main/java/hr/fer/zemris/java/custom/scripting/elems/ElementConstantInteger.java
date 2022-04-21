package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element class used to store int value
 * I.e. <code>123</code> will be parsed into this class
 *
 * @author franzekan
 */
public class ElementConstantInteger extends Element {
    private final int value;

    /**
     * Instantiates a new Element constant integer.
     *
     * @param value number
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Value getter
     *
     * @return value value
     */
    public int getValue() {
        return value;
    }

    @Override
    public String asText() {
        return String.valueOf(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementConstantInteger that = (ElementConstantInteger) o;

        return value == that.value;
    }
}
