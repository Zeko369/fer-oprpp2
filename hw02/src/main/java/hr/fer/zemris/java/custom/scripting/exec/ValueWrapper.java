package hr.fer.zemris.java.custom.scripting.exec;

/**
 * The type Value wrapper.
 *
 * @author franzekan
 */
public class ValueWrapper {
    private Object value;

    /**
     * Instantiates a new Value wrapper.
     *
     * @param value the value
     */
    public ValueWrapper(Object value) {
        this.value = value;
    }

    /**
     * Copy value wrapper.
     *
     * @param value the value
     * @return the value wrapper
     */
    public static ValueWrapper copy(ValueWrapper value) {
        return new ValueWrapper(value.getValue());
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Add.
     *
     * @param incValue the inc value
     */
// TODO: Add cast to ValueWrapper where if other is ValueWrapper to auto unbox it
    public void add(Object incValue) {
        Number a = this.coarseIntoNumber(this.value);
        Number b = this.coarseIntoNumber(incValue);

        if (areDouble(a, b)) {
            this.value = a.doubleValue() + b.doubleValue();
        } else {
            this.value = a.intValue() + b.intValue();
        }
    }

    /**
     * Subtract.
     *
     * @param decValue the dec value
     */
    public void subtract(Object decValue) {
        Number a = this.coarseIntoNumber(this.value);
        Number b = this.coarseIntoNumber(decValue);

        if (areDouble(a, b)) {
            this.value = a.doubleValue() - b.doubleValue();
        } else {
            this.value = a.intValue() - b.intValue();
        }
    }

    /**
     * Multiply.
     *
     * @param mulValue the mul value
     */
    public void multiply(Object mulValue) {
        Number a = this.coarseIntoNumber(this.value);
        Number b = this.coarseIntoNumber(mulValue);

        if (areDouble(a, b)) {
            this.value = a.doubleValue() * b.doubleValue();
        } else {
            this.value = a.intValue() * b.intValue();
        }
    }

    /**
     * Divide.
     *
     * @param divValue the div value
     */
    public void divide(Object divValue) {
        Number a = this.coarseIntoNumber(this.value);
        Number b = this.coarseIntoNumber(divValue);

        if (areDouble(a, b)) {
            this.value = a.doubleValue() / b.doubleValue();
        } else {
            this.value = a.intValue() / b.intValue();
        }
    }

    /**
     * Num compare int.
     *
     * @param withValue the with value
     * @return the int
     */
    public int numCompare(Object withValue) {
        Number a = this.coarseIntoNumber(this.value);
        Number b = this.coarseIntoNumber(withValue);

        return areDouble(a, b) ? Double.compare(a.doubleValue(), b.doubleValue()) : Integer.compare(a.intValue(), b.intValue());
    }

    private boolean areDouble(Number a, Number b) {
        return (a instanceof Double || b instanceof Double);
    }

    /**
     * As number number.
     *
     * @return the number
     */
    public Number asNumber() {
        return this.coarseIntoNumber(this.value);
    }

    private Number coarseIntoNumber(Object arg) {
        if (arg == null) {
            return 0;
        }

        if (arg instanceof String) {
            if (((String) arg).matches("[0-9]+")) {
                return Integer.parseInt((String) arg);
            }

            try {
                return Double.parseDouble((String) arg);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid number format!");
            }
        }

        if (arg instanceof Integer) {
            return (Integer) arg;
        }

        if (arg instanceof Double) {
            return (Double) arg;
        }

        throw new RuntimeException("Argument is not number-like, but " + arg.getClass().getName());
    }
}
