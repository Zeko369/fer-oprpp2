package hr.fer.oprpp2.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Helper for getting a theme from request color
 *
 * @author franzekan
 */
public class Theme {
    /**
     * Gets from request.
     *
     * @param req the req
     * @return the from request
     */
    public static String getFromRequest(HttpServletRequest req) {
        String value = Objects.requireNonNullElse((String) req.getSession().getAttribute("pickedFgColor"), "#000000");
        return value.equals("#000000") ? "light" : "dark";
    }
}
