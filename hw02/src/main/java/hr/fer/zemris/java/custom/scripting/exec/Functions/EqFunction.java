package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * Check if 2 values are equal, if true return third
 * Usage: <code>eq(x, y, ifTrueValue)</code>
 *
 * @author franzekan
 */
public class EqFunction extends BaseFunction {
    /**
     * Instantiates a new Eq function.
     */
    public EqFunction() {
        super(3);
    }

    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments, String.class, String.class, String.class);

        String first = (String) arguments[0].getValue();
        String second = (String) arguments[1].getValue();

        if (first.equals(second)) {
            functionContext.stack().push(arguments[2]);
        }
    }
}
