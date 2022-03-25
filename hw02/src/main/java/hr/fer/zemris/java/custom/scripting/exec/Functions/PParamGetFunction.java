package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class PParamGetFunction extends BaseFunction {
    public PParamGetFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, String.class, Object.class);

        String name = (String) arguments[0];
        String defaultValue = (String) arguments[1];

        String value = functionContext.rc().getParameter(name);
        functionContext.stack().push(new ValueWrapper(value == null ? defaultValue : value));
    }
}
