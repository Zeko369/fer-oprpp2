package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * The type Sin function.
 *
 * @author franzekan
 */
public class SinFunction extends BaseFunction {
    /**
     * Instantiates a new Sin function.
     */
    public SinFunction() {
        super(1);
    }

    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments, Number.class);

        double argument = ((Number) arguments[0].getValue()).doubleValue();
        functionContext.stack().push(new ValueWrapper(Math.sin(argument)));
    }
}
