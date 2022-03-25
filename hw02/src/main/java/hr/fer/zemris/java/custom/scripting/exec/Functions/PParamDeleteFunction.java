package hr.fer.zemris.java.custom.scripting.exec.Functions;

public class PParamDeleteFunction extends BaseFunction {
    public PParamDeleteFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, String.class);

        String name = (String) arguments[0];
        functionContext.rc().removePersistentParameter(name);
    }
}
