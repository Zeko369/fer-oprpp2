package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class DuplicateFunction extends BaseFunction {
    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments);
        functionContext.stack().push(functionContext.stack().peek());
    }
}
