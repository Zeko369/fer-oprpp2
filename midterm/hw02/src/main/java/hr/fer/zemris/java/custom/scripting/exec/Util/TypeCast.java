package hr.fer.zemris.java.custom.scripting.exec.Util;

/**
 * The type Type cast.
 *
 * @author franzekan
 */
@Deprecated
public class TypeCast {
    /**
     * To double double.
     *
     * @param value   the value
     * @param message the message
     * @return the double
     */
    public static Double toDouble(Object value, String message) {
        try {
            return switch (value) {
                case Integer integer -> (double) integer;
                case Double aDouble -> aDouble;
                case String s -> Double.parseDouble((String) value);
                default -> throw new RuntimeException(message);
            };
        } catch (NumberFormatException e) {
            throw new RuntimeException(message);
        }
    }

    /**
     * To double double.
     *
     * @param value the value
     * @return the double
     */
    public static Double toDouble(Object value) {
        return toDouble(value, "Invalid type of value.");
    }
}
