package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.Util.ValuePrinter;

public class TParamSetFunction extends BaseFunction {
    public TParamSetFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, Object.class, String.class);

        String name = (String) arguments[1];
        String value = ValuePrinter.toString(arguments[0]);
        functionContext.rc().setTemporaryParameter(name, value);
    }
}
