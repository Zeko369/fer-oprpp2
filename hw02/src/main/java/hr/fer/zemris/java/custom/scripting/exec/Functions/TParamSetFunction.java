package hr.fer.zemris.java.custom.scripting.exec.Functions;

public class TParamSetFunction extends BaseFunction {
    public TParamSetFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, Object.class, String.class);

        String name = (String) arguments[1];
        String value = arguments[0].toString();
        functionContext.rc().setTemporaryParameter(name, value);
    }
}
