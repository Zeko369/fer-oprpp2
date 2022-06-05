package hr.fer.oprpp2.servlets.Auth;

import hr.fer.oprpp2.services.Auth.AuthResult;
import hr.fer.oprpp2.services.Auth.AuthService;
import hr.fer.oprpp2.services.Auth.RegisterError;
import hr.fer.oprpp2.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/servlet/auth/register")
public class RegisterServlet extends BaseServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: handle redirect if session exists
        req.getRequestDispatcher("/WEB-INF/pages/auth/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String password = req.getParameter("password");

        AuthResult<RegisterError> registerRes = this.authService.register(username, firstName, lastName, email, password);
        switch (registerRes.enumType()) {
            case EMAIL_IN_USE -> {
                req.setAttribute("error", "Email in use");
                req.setAttribute("email", email);
                req.setAttribute("firstName", firstName);
                req.setAttribute("lastName", lastName);
                req.setAttribute("username", username);
                req.getRequestDispatcher("/WEB-INF/pages/auth/register.jsp").forward(req, resp);
            }
            case USERNAME_IN_USE -> {
                req.setAttribute("error", "Username in use");
                req.setAttribute("email", email);
                req.setAttribute("firstName", firstName);
                req.setAttribute("lastName", lastName);
                req.setAttribute("username", username);
                req.getRequestDispatcher("/WEB-INF/pages/auth/register.jsp").forward(req, resp);
            }
            case OK -> {
                req.getSession().setAttribute("userId", registerRes.userId());
                req.getSession().setAttribute("username", username);

                resp.sendRedirect("/blog-app");
            }
            case ERROR -> this.throwError(req, resp, "Error while registering in");
        }
    }
}
