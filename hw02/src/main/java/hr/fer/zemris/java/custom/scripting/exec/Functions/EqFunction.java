package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class EqFunction extends BaseFunction {
    public EqFunction() {
        super(3);
    }

    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments, String.class, String.class, String.class);

        String first = (String) arguments[0].getValue();
        String second = (String) arguments[1].getValue();

        if (first.equals(second)) {
            functionContext.stack().push(arguments[2]);
        }
    }
}
