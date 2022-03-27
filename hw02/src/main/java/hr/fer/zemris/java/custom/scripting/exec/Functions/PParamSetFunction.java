package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class PParamSetFunction extends BaseFunction {
    public PParamSetFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments, Object.class, String.class);

        String name = (String) arguments[1].getValue();
        String value = arguments[0].getValue().toString(); // TODO: check how to format this if number
        functionContext.rc().setPersistentParameter(name, value);
    }
}
