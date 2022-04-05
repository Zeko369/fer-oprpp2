package hr.fer.oprpp2.util;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public record ValueWithErrors<T>(T value, List<String> errors) {
    public boolean isInvalid() {
        return this.errors != null && !this.errors.isEmpty();
    }

    public static List<String> getAllErrors(ValueWithErrors<?>... values) {
        List<String> allErrors = new ArrayList<>();
        for (ValueWithErrors<?> value : values) {
            if (value.isInvalid()) {
                allErrors.addAll(value.errors);
            }
        }

        return allErrors;
    }

    public static ValueWithErrors<Integer> ofParam(HttpServletRequest req, String name) {
        String value = req.getParameter(name);

        if (value == null) {
            return new ValueWithErrors<>(null, List.of("Parameter " + name + " is missing"));
        }

        try {
            return new ValueWithErrors<>(Integer.parseInt(value), null);
        } catch (NumberFormatException e) {
            return new ValueWithErrors<>(null, List.of("Parameter " + name + " is not a number"));
        }
    }

    public static ValueWithErrors<Integer> ofParamWithDefault(HttpServletRequest req, String name, int defaultValue) {
        if (req.getParameter(name) == null) {
            return new ValueWithErrors<>(defaultValue, null);
        }

        return ofParam(req, name);
    }
}
