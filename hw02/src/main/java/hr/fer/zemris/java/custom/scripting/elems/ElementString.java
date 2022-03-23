package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element class used to store a string literal
 * I.e. <code>"FooBar"</code> will be parsed into this class
 *
 * @author franzekan
 */
public class ElementString extends Element {
    private final String value;

    /**
     * Instantiates a new Element string.
     *
     * @param value the value
     */
    public ElementString(String value) {
        this.value = value;
    }

    /**
     * Value getter
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    @Override
    public String asText() {
        return String.format("\"%s\"", this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementString that = (ElementString) o;

        if (this.value != null) {
            return this.value.equals(that.value);
        }

        return that.value == null;
    }
}
