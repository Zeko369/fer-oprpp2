package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element class used to store a variable
 * I.e. <code>foo</code> will be parsed into this class
 *
 * @author franzekan
 */
public class ElementVariable extends Element {
    private final String name;

    /**
     * Instantiates a new Element variable.
     *
     * @param name the name
     */
    public ElementVariable(String name) {
        this.name = name;
    }

    @Override
    public String asText() {
        return this.name;
    }

    /**
     * Name getter
     *
     * @return the name
     */
    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementVariable that = (ElementVariable) o;

        if (this.name != null) {
            return this.name.equals(that.name);
        }

        return that.name == null;
    }
}
