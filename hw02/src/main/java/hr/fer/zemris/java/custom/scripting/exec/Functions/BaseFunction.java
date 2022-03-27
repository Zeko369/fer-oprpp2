package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public abstract class BaseFunction {
    public final int requiredArguments;

    public BaseFunction(int requiredArguments) {
        this.requiredArguments = requiredArguments;
    }

    public BaseFunction() {
        this(0);
    }

    public int getRequiredArguments() {
        return requiredArguments;
    }

    protected void checkArguments(ValueWrapper[] args, Class<?>... argumentBaseClasses) {
        if (args.length != requiredArguments) {
            throw new IllegalArgumentException("Invalid number of arguments!");
        }

        if (argumentBaseClasses.length != requiredArguments) {
            System.err.println("Ignoring argument type checking");
            return;
        }

        // FIXME: check if this still works after ValueWrapper refactor
        for (int i = 0; i < argumentBaseClasses.length; i++) {
            if (!argumentBaseClasses[i].isInstance(args[i].getValue())) {
                throw new IllegalArgumentException(String.format("Invalid type of argument [%d] expected %s got %s", i, argumentBaseClasses[i], args[i].getClass()));
            }
        }
    }

    public abstract void apply(FunctionContext functionContext, ValueWrapper[] arguments);
}
