package hr.fer.zemris.java.custom.scripting.exec;

public record ValueWrapper (Object value, String name) {
    public static final String NO_NAME_CONSTANT = "[NO_NAME]";

    public ValueWrapper(Object value) {
        this(value, ValueWrapper.NO_NAME_CONSTANT);
    }

    public int asInt() {
        return (int) value;
    }
}
