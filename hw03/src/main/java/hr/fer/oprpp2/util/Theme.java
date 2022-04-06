package hr.fer.oprpp2.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class Theme {
    public static String getFromRequest(HttpServletRequest req) {
        String value = Objects.requireNonNullElse((String) req.getSession().getAttribute("pickedFgColor"), "#000000");
        return value.equals("#000000") ? "light" : "dark";
    }
}
