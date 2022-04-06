package hr.fer.oprpp2.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract class BaseServlet extends HttpServlet {
    protected void throwError(HttpServletRequest req, HttpServletResponse resp, List<String> errors) throws ServletException, IOException {
        req.setAttribute("error", String.join("<br/>", errors));
        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
    }
}
