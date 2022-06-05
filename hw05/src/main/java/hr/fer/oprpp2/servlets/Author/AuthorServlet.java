package hr.fer.oprpp2.servlets.Author;

import hr.fer.oprpp2.dao.DAOException;
import hr.fer.oprpp2.model.BlogEntry;
import hr.fer.oprpp2.model.BlogUser;
import hr.fer.oprpp2.router.RouteMatch;
import hr.fer.oprpp2.router.SimpleRouter;
import hr.fer.oprpp2.services.AuthorService;
import hr.fer.oprpp2.services.BlogService;
import hr.fer.oprpp2.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/servlet/author/*")
public class AuthorServlet extends BaseServlet {
    private final SimpleRouter router = new SimpleRouter();
    private final BlogService blogService = new BlogService();
    private final AuthorService authorService = new AuthorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getPathInfo() == null) {
            this.throwError(req, resp, "Route not found?");
            return;
        }

        RouteMatch match = router.getRoute(req.getPathInfo().substring(1));
        try {
            switch (match.authorRoute()) {
                case AUTHOR -> this.showAuthor(req, resp, match);
                case BLOG_SHOW -> this.showBlog(req, resp, match);
                case AUTHOR_EDIT_BLOG -> this.editBlog(req, resp, match);
                case AUTHOR_NEW_BLOG -> this.newBlog(req, resp, match);
            }
        } catch (DAOException e) {
            this.throwError(req, resp, e.getMessage());
        }
    }

    private void showAuthor(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws IOException, ServletException, DAOException {
        System.out.println("HEREJk");
        Optional<BlogUser> author = this.authorService.getAuthor(match.authorUsername());
        if (author.isEmpty()) {
            this.throwError(req, resp, "Author not found");
            return;
        }

        req.setAttribute("author", author.get());
        req.setAttribute("blogs", this.blogService.getBlogsForUser(author.get().getId()));
        req.getRequestDispatcher("/WEB-INF/pages/author/show.jsp").forward(req, resp);
    }

    private void showBlog(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws IOException, ServletException, DAOException {
        Optional<BlogEntry> blog = this.blogService.getBlog(match.blogId());
        if (blog.isEmpty()) {
            this.throwError(req, resp, "Blog not found");
            return;
        }

        req.setAttribute("blog", blog);
        req.getRequestDispatcher("/WEB-INF/pages/blogs/show.jsp").forward(req, resp);
    }

    private void editBlog(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws IOException, ServletException, DAOException {
        this.throwError(req, resp, "Not implemented");
    }

    private void newBlog(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws IOException, ServletException, DAOException {
        this.throwError(req, resp, "Not implemented");
    }
}
