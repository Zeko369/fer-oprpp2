package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * Base function that's used to make functions inside SmartScript
 *
 * @author franzekan
 */
public abstract class BaseFunction {
    /**
     * The Required arguments.
     */
    public final int requiredArguments;

    /**
     * Instantiates a new Base function.
     *
     * @param requiredArguments the required arguments
     */
    public BaseFunction(int requiredArguments) {
        this.requiredArguments = requiredArguments;
    }

    /**
     * Instantiates a new Base function.
     */
    public BaseFunction() {
        this(0);
    }

    /**
     * Gets required arguments.
     *
     * @return the required arguments
     */
    public int getRequiredArguments() {
        return requiredArguments;
    }

    /**
     * Method used to check if the arguments are valid (count and types)
     *
     * @param args                the args
     * @param argumentBaseClasses the argument base classes
     */
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

    /**
     * Run function
     *
     * @param functionContext the function context
     * @param arguments       the arguments
     */
    public abstract void apply(FunctionContext functionContext, ValueWrapper[] arguments);
}
