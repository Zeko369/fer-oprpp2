package hr.fer.oprpp2.servlets;

import hr.fer.oprpp2.Color;
import hr.fer.oprpp2.ColorsConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SetColorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String colorName = req.getParameter("color");

        if (colorName == null) {
            req.setAttribute("error", "No color chosen");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        try {
            Color color = ColorsConfig.getColor(colorName);
            session.setAttribute("pickedBgColor", color.hexBg());
            session.setAttribute("pickedFgColor", color.hexFg());

            resp.sendRedirect("/");
        } catch(IllegalArgumentException e) {
            req.setAttribute("error", "Invalid color selected");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }
}
