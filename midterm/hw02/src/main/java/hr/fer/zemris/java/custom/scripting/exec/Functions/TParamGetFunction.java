package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * The type T param get function.
 *
 * @author franzekan
 */
public class TParamGetFunction extends BaseFunction {
    /**
     * Instantiates a new T param get function.
     */
    public TParamGetFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments, String.class, Object.class);

        String name = (String) arguments[0].getValue();
        Object value = functionContext.rc().getTemporaryParameter(name);
        functionContext.stack().push(value == null ? arguments[1] : new ValueWrapper(value));
    }
}
