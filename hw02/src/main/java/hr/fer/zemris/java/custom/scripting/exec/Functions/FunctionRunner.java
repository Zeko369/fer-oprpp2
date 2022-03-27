package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

import java.util.HashMap;
import java.util.Map;

public class FunctionRunner {
    private static final Map<String, BaseFunction> functions = new HashMap<>();

    static {
        functions.put("sin", new SinFunction());
        functions.put("eq", new EqFunction());
        functions.put("decfmt", new DecFMTFunction());
        functions.put("dup", new DuplicateFunction());
        functions.put("swap", new SwapFunction());
        functions.put("setMimeType", new SetMimeTypeFunction());
        functions.put("paramGet", new ParamGetFunction());
        functions.put("pparamGet", new PParamGetFunction());
        functions.put("pparamSet", new PParamSetFunction());
        functions.put("pparamDel", new PParamDeleteFunction());
        functions.put("tparamGet", new TParamGetFunction());
        functions.put("tparamSet", new TParamSetFunction());
        functions.put("tparamDel", new TParamDeleteFunction());
        functions.put("dynamicRead", new DynamicReadParamFunction());
    }

    public static void run(String name, FunctionContext context) {
        BaseFunction function = functions.get(name);
        if (function == null) {
            throw new RuntimeException("Function " + name + " not found.");
        }

        int argsCount = function.getRequiredArguments();
        if (context.stack().size() < argsCount) {
            throw new RuntimeException("Function " + name + " expects " + argsCount + " arguments." + " Only " + context.stack().size() + " arguments provided.");
        }

        ValueWrapper[] args = new ValueWrapper[argsCount];
        for (int i = argsCount - 1; i >= 0; i--) {
            args[i] = context.stack().pop();
        }

        function.apply(context, args);
    }
}
