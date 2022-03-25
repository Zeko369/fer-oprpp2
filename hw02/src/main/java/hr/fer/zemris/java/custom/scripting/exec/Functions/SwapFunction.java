package hr.fer.zemris.java.custom.scripting.exec.Functions;

public class SwapFunction extends BaseFunction {
    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments);

        Object a = functionContext.stack().pop();
        Object b = functionContext.stack().pop();
        functionContext.stack().push(a);
        functionContext.stack().push(b);
    }
}

