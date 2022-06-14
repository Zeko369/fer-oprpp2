package hr.fer.oprpp2.servlets.Author;

import hr.fer.oprpp2.dao.DAOException;
import hr.fer.oprpp2.model.BlogEntry;
import hr.fer.oprpp2.model.BlogUser;
import hr.fer.oprpp2.router.RouteMatch;
import hr.fer.oprpp2.router.SimpleRouter;
import hr.fer.oprpp2.services.AuthorService;
import hr.fer.oprpp2.services.AuthorizeBlog;
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
    private final AuthorizeBlog authorizeBlog = new AuthorizeBlog();

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getPathInfo() == null) {
            this.throwError(req, resp, "Route not found?");
            return;
        }

        RouteMatch match = router.getRoute(req.getPathInfo().substring(1));
        try {
            System.out.println(match.authorRoute());
            switch (match.authorRoute()) {
                case AUTHOR -> this.commentAuthor(req, resp, match);
                case AUTHOR_EDIT_BLOG -> this.updateBlog(req, resp, match);
                case AUTHOR_NEW_BLOG -> this.createBlog(req, resp, match);
                case AUTHOR_COMMENT_BLOG -> this.commentBlog(req, resp, match);
            }
        } catch (DAOException e) {
            this.throwError(req, resp, e.getMessage());
        }
    }

    private void commentAuthor(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws ServletException, DAOException, IOException {
        Optional<BlogUser> author = this.authorService.getAuthor(match.authorUsername());
        if (author.isEmpty()) {
            this.throwError(req, resp, "Author not found!");
            return;
        }

        String comment = req.getParameter("comment");
        if (comment == null || comment.isEmpty()) {
            this.throwError(req, resp, "Comment is empty!");
            return;
        }

        String commenterUsername = (String) req.getSession().getAttribute("username");
        Optional<BlogUser> commenter = this.authorService.getAuthor(commenterUsername);
        if (commenter.isEmpty()) {
            this.throwError(req, resp, "Commenter not found!");
            return;
        }

        this.authorService.commentAuthor(author.get(), commenter.get(), comment);
        resp.sendRedirect("/blog-app/servlet/author/" + author.get().getUsername());
    }

    private void createBlog(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws IOException, ServletException, DAOException {
        if (this.authorizeBlog.authorize(this, req, resp, match) == null) {
            return;
        }
        Optional<BlogUser> author = this.authorService.getAuthor(match.authorUsername());
        if (author.isEmpty()) {
            this.throwError(req, resp, "Author not found!");
            return;
        }

        BlogEntry blog = this.blogService.createBlog(author.get(), req.getParameter("title"), req.getParameter("body"));
        resp.sendRedirect("/blog-app/servlet/author/" + req.getSession().getAttribute("username") + "/" + blog.getId());
    }

    private void updateBlog(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws IOException, ServletException, DAOException {
        BlogEntry blog = this.authorizeBlog.authorize(this, req, resp, match);
        if (blog == null) {
            return;
        }

        this.blogService.updateBlog(blog, req.getParameter("title"), req.getParameter("body"));
        resp.sendRedirect("/blog-app/servlet/author/" + req.getSession().getAttribute("username") + "/" + blog.getId());
    }

    private void commentBlog(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws ServletException, IOException {
        try {
            this.blogService.comment(match.blogId(), req.getParameter("message"), req.getParameter("email"));
        } catch (DAOException e) {
            this.throwError(req, resp, e.getMessage());
        }

        resp.sendRedirect("/blog-app/servlet/author/" + match.authorUsername() + "/" + match.blogId());
    }

    private void showAuthor(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws IOException, ServletException, DAOException {
        Optional<BlogUser> author = this.authorService.getAuthor(match.authorUsername());
        if (author.isEmpty()) {
            this.throwError(req, resp, "Author not found");
            return;
        }

        req.setAttribute("author", author.get());
        req.setAttribute("isAuthor", match.authorUsername().equals(req.getSession().getAttribute("username")));
        req.setAttribute("isLoggedIn", req.getSession().getAttribute("username") != null);
        req.setAttribute("blogs", this.blogService.getBlogsForUser(author.get().getId()));

        req.getRequestDispatcher("/WEB-INF/pages/author/show.jsp").forward(req, resp);
    }

    private void showBlog(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws IOException, ServletException, DAOException {
        Optional<BlogEntry> blog = this.blogService.getBlog(match.blogId());
        if (blog.isEmpty()) {
            this.throwError(req, resp, "Blog not found");
            return;
        }

        req.setAttribute("blog", blog.get());
        req.setAttribute("isAuthor", match.authorUsername().equals(req.getSession().getAttribute("username")));
        req.getRequestDispatcher("/WEB-INF/pages/blog/show.jsp").forward(req, resp);
    }

    private void newBlog(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws IOException, ServletException, DAOException {
        if (this.authorizeBlog.authorize(this, req, resp, match) == null) {
            return;
        }

        req.getRequestDispatcher("/WEB-INF/pages/blog/form.jsp").forward(req, resp);
    }

    private void editBlog(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws IOException, ServletException, DAOException {
        BlogEntry blog = this.authorizeBlog.authorize(this, req, resp, match);
        if (blog == null) {
            return;
        }

        req.setAttribute("blog", blog);
        req.setAttribute("title", blog.getTitle());
        req.setAttribute("body", blog.getBody());
        req.getRequestDispatcher("/WEB-INF/pages/blog/form.jsp").forward(req, resp);
    }
}
