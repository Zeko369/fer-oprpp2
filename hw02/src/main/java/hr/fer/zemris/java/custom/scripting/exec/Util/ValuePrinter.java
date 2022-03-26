package hr.fer.zemris.java.custom.scripting.exec.Util;

public class ValuePrinter {
    public static String toString(Object value) {
        if (value instanceof Integer) {
            return Integer.toString((Integer) value);
        } else if (value instanceof Double) {
            String tmp = Double.toString((Double) value);
            if (tmp.endsWith(".0")) {
                tmp = tmp.substring(0, tmp.length() - 2);
            }

            return tmp;
        } else {
            return value.toString();
        }
    }
}
