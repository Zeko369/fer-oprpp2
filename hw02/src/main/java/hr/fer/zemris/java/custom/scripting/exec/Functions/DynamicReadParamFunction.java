package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class DynamicReadParamFunction extends BaseFunction {
    public DynamicReadParamFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments, String.class, Object.class);

        String a = arguments[0].getValue().toString();
        String b = arguments[1].getValue().toString();

        String key = a + ":" + b;
        String var = functionContext.rc().getTemporaryParameter(key);
        if (var != null) {
            functionContext.stack().push(new ValueWrapper(var));
        }
    }
}
