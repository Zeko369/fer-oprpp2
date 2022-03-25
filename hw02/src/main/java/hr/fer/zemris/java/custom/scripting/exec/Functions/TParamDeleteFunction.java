package hr.fer.zemris.java.custom.scripting.exec.Functions;

public class TParamDeleteFunction extends BaseFunction {
    public TParamDeleteFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, String.class);

        String name = (String) arguments[0];
        functionContext.rc().removeTemporaryParameter(name);
    }
}