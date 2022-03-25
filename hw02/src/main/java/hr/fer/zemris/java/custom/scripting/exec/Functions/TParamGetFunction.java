package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class TParamGetFunction extends BaseFunction {
    public TParamGetFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, String.class, Object.class);

        String name = (String) arguments[0];
        Object value = functionContext.rc().getTemporaryParameter(name);
        functionContext.stack().push(value == null ? arguments[1] : value);
    }
}
