package hr.fer.zemris.java.custom.scripting.exec.Functions;

public class EqFunction extends BaseFunction {
    public EqFunction() {
        super(3);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, String.class, String.class, String.class);

        String first = (String) arguments[0];
        String second = (String) arguments[1];

        if (first.equals(second)) {
            functionContext.stack().push(arguments[2]);
        }
    }
}
