package hr.fer.oprpp2.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {
    public record TrigonometricValue(int angle, String sin, String cos) {
        private static final DecimalFormat df = new DecimalFormat("0.0000");

        public TrigonometricValue(int angle, double sin, double cos) {
            this(angle, df.format(sin), df.format(cos));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int a = this.getDefaultParam(req, "a", 0);
        int b = this.getDefaultParam(req, "b", 360);

        if (a > b) {
            a += b;
            b = a - b;
            a -= b;
        }

        while(b > a + 720) {
            a += 720;
        }

        List<TrigonometricValue> values = new ArrayList<>();
        for (int i = a; i <= b; i++) {
            double sin = Math.sin(Math.toRadians(i));
            double cos = Math.cos(Math.toRadians(i));

            values.add(new TrigonometricValue(i, sin, cos));
        }

        req.setAttribute("values", values);
        req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
    }

    private int getDefaultParam(HttpServletRequest req, String key, int defaultValue) {
        String val = req.getParameter(key);
        if (val == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
