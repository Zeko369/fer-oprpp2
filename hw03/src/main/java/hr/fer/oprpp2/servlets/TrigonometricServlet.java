package hr.fer.oprpp2.servlets;

import hr.fer.oprpp2.util.ValueWithErrors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/trigonometric")
public class TrigonometricServlet extends BaseServlet {
    public record TrigonometricValue(int angle, String sin, String cos) {
        private static final DecimalFormat df = new DecimalFormat("0.0000");

        public TrigonometricValue(int angle, double sin, double cos) {
            this(angle, df.format(sin), df.format(cos));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ValueWithErrors<Integer> wrappedA = ValueWithErrors.ofParamWithDefault(req, "a", 0);
        ValueWithErrors<Integer> wrappedB = ValueWithErrors.ofParamWithDefault(req, "b", 360);

        if (wrappedA.isInvalid() || wrappedB.isInvalid()) {
            this.throwError(req, resp, ValueWithErrors.getAllErrors(wrappedA, wrappedB), 400);
            return;
        }

        int a = wrappedA.value();
        int b = wrappedB.value();

        if (a > b) {
            a += b;
            b = a - b;
            a -= b;
        }

        while (b > a + 720) {
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
}
