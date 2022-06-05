package hr.fer.oprpp2.servlets.Auth;

import hr.fer.oprpp2.services.Auth.AuthService;
import hr.fer.oprpp2.services.Auth.LoginError;
import hr.fer.oprpp2.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/servlet/auth/login")
public class LoginServlet extends BaseServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: handle redirect if session exists
        req.getRequestDispatcher("/WEB-INF/pages/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        LoginError error = this.authService.login(username, password);
        switch (error) {
            case USER_NOT_FOUND -> {
                req.setAttribute("error", "Username not found");
                req.setAttribute("username", username);
                req.getRequestDispatcher("/WEB-INF/pages/auth/login.jsp").forward(req, resp);
            }
            case WRONG_PASSWORD -> {
                req.setAttribute("error", "Wrong password");
                req.setAttribute("username", username);
                req.getRequestDispatcher("/WEB-INF/pages/auth/login.jsp").forward(req, resp);
            }
            case OK -> {
                // TODO: setup session here
                resp.sendRedirect("/blog-app");
            }
            case ERROR -> this.throwError(req, resp, "Error while logging in");
        }
    }
}
