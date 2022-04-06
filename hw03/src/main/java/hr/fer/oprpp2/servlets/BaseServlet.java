package hr.fer.oprpp2.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract class BaseServlet extends HttpServlet {
    protected void throwError(HttpServletRequest req, HttpServletResponse resp, String error) throws ServletException, IOException {
        this.throwError(req, resp, List.of(error), 500);
    }

    protected void throwError(HttpServletRequest req, HttpServletResponse resp, String error, int statusCode) throws ServletException, IOException {
        this.throwError(req, resp, List.of(error), statusCode);
    }

    protected void throwError(HttpServletRequest req, HttpServletResponse resp, List<String> errors, int statusCode) throws ServletException, IOException {
        resp.setStatus(statusCode);
        req.setAttribute("error", String.join("<br/>", errors));
        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
    }
}
