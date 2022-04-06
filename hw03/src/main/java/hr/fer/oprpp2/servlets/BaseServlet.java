package hr.fer.oprpp2.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * BaseServlet with helpers for returning errors
 *
 * @author franzekan
 */
public abstract class BaseServlet extends HttpServlet {
    /**
     * Throw error.
     *
     * @param req   the req
     * @param resp  the resp
     * @param error the error
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    protected void throwError(HttpServletRequest req, HttpServletResponse resp, String error) throws ServletException, IOException {
        this.throwError(req, resp, List.of(error), 500);
    }

    /**
     * Throw error.
     *
     * @param req        the req
     * @param resp       the resp
     * @param error      the error
     * @param statusCode the status code
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    protected void throwError(HttpServletRequest req, HttpServletResponse resp, String error, int statusCode) throws ServletException, IOException {
        this.throwError(req, resp, List.of(error), statusCode);
    }

    /**
     * Throw error.
     *
     * @param req        the req
     * @param resp       the resp
     * @param errors     the errors
     * @param statusCode the status code
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    protected void throwError(HttpServletRequest req, HttpServletResponse resp, List<String> errors, int statusCode) throws ServletException, IOException {
        resp.setStatus(statusCode);
        req.setAttribute("error", String.join("<br/>", errors));
        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
    }
}
