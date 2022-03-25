package hr.fer.zemris.java.custom.scripting.exec;

public record ValueWrapper(Object value, String name) {
    public int asInt() {
        return (int) value;
    }
}
