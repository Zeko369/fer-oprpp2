package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element class used to store a function annotation
 * I.e. <code>@sin</code> will be parsed into this class
 *
 * @author franzekan
 */
public class ElementFunction extends Element {
    private final String name;

    /**
     * Instantiates a new Element function.
     *
     * @param name the name
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    /**
     * Name getter
     *
     * @return name name
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String asText() {
        return String.format("@%s", this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementFunction that = (ElementFunction) o;

        if (this.name != null) {
            return this.name.equals(that.name);
        }

        return that.name == null;
    }
}
