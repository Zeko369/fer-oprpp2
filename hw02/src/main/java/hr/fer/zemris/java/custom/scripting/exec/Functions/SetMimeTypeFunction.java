package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class SetMimeTypeFunction extends BaseFunction {
    public SetMimeTypeFunction() {
        super(1);
    }

    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments, String.class);
        functionContext.rc().setMimeType((String) arguments[0].getValue());
    }
}
