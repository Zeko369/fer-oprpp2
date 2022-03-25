package hr.fer.zemris.java.custom.scripting.exec.Functions;

public class SetMimeTypeFunction extends BaseFunction {
    public SetMimeTypeFunction() {
        super(1);
    }

    @Override
    public void apply(FunctionContext functionContext, Object[] arguments) {
        this.checkArguments(arguments, String.class);
        functionContext.rc().setMimeType(String.valueOf(arguments[0]));
    }
}
