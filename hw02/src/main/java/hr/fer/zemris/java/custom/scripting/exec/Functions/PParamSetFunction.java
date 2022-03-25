package hr.fer.zemris.java.custom.scripting.exec.Functions;

public class PParamSetFunction extends BaseFunction {
    public PParamSetFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, Object.class, String.class);

        String name = (String) arguments[1];
        String value = arguments[0].toString();
        functionContext.rc().setPersistentParameter(name, value);
    }
}
