package hr.fer.zemris.java.custom.scripting.exec.Functions;

public class SinFunction extends BaseFunction {
    public SinFunction() {
        super(1);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, Number.class);
        functionContext.stack().push(Math.sin((double) arguments[0]));
    }
}
