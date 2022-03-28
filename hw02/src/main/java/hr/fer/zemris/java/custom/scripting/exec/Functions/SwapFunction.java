package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * The type Swap function.
 *
 * @author franzekan
 */
public class SwapFunction extends BaseFunction {
    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments);

        ValueWrapper a = functionContext.stack().pop();
        ValueWrapper b = functionContext.stack().pop();
        functionContext.stack().push(a);
        functionContext.stack().push(b);
    }
}

