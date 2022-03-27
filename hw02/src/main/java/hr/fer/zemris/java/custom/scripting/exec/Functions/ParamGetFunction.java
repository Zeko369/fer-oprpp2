package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class ParamGetFunction extends BaseFunction {
    public ParamGetFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments, String.class, Object.class);

        String name = (String) arguments[0].getValue();
        Object value = functionContext.rc().getParameter(name);
        functionContext.stack().push(value == null ? arguments[1] : new ValueWrapper(value));
    }
}
