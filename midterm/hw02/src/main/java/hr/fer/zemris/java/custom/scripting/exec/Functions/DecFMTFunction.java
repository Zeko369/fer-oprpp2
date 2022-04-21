package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

import java.text.DecimalFormat;

/**
 * The type Dec fmt function.
 * Usage: <code>decfmt(value, pattern)</code>
 *
 * @author franzekan
 */
public class DecFMTFunction extends BaseFunction {
    /**
     * Instantiates a new Dec fmt function.
     */
    public DecFMTFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments, Object.class, String.class);

        DecimalFormat df = new DecimalFormat((String) arguments[1].getValue());
        functionContext.stack().push(new ValueWrapper(df.format(arguments[0].asNumber())));
    }
}
