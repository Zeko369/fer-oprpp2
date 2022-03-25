package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class SinFunction extends BaseFunction {
    public SinFunction() {
        super(1);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, Number.class);
        functionContext.stack().push(new ValueWrapper(Math.sin((double) arguments[0])));
    }
}
