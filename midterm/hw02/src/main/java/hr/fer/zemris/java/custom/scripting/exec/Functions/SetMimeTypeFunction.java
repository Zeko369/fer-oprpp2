package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * The type Set MIME type function.
 *
 * @author franzekan
 */
public class SetMimeTypeFunction extends BaseFunction {
    /**
     * Instantiates a new Set mime type function.
     */
    public SetMimeTypeFunction() {
        super(1);
    }

    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments, String.class);
        functionContext.rc().setMimeType((String) arguments[0].getValue());
    }
}
