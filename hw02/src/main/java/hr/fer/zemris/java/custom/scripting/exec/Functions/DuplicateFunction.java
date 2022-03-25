package hr.fer.zemris.java.custom.scripting.exec.Functions;

public class DuplicateFunction extends BaseFunction {
    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments);
        functionContext.stack().push(functionContext.stack().peek());
    }
}
