package hr.fer.oprpp2.servlets.Blogs;

import hr.fer.oprpp2.dao.DAOException;
import hr.fer.oprpp2.dao.DAOProvider;
import hr.fer.oprpp2.model.BlogEntry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/servlet/blogs")
public class BlogsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<BlogEntry> blogs = DAOProvider.getDAO().getBlogs();
            req.setAttribute("blogs", blogs);
            req.getRequestDispatcher("/WEB-INF/pages/blog/index.jsp").forward(req, resp);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }
}
