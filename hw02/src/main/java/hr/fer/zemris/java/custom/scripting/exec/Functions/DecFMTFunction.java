package hr.fer.zemris.java.custom.scripting.exec.Functions;

import java.text.DecimalFormat;

/**
 * decfmt(x, f)
 */
public class DecFMTFunction extends BaseFunction {
    public DecFMTFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, Object.class, String.class);

        Double tmp = switch (arguments[0]) {
            case Integer integer -> (double) (int) arguments[0];
            case Double aDouble -> (double) arguments[0];
            case String s -> Double.parseDouble(s);
            case null, default -> throw new IllegalArgumentException("Invalid type of argument.");
        };

        DecimalFormat df = new DecimalFormat(arguments[1].toString());
        functionContext.stack().push(df.format(tmp));
    }
}
