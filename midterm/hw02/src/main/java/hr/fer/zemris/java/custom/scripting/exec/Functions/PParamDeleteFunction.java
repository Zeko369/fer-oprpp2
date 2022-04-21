package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * Function used to delete cookie
 *
 * @author franzekan
 */
public class PParamDeleteFunction extends BaseFunction {
    /**
     * Instantiates a new P param delete function.
     */
    public PParamDeleteFunction() {
        super(2);
    }

    @Override
    public void apply(FunctionContext functionContext, ValueWrapper[] arguments) {
        this.checkArguments(arguments, String.class);

        String name = (String) arguments[0].getValue();
        functionContext.rc().removePersistentParameter(name);
    }
}
