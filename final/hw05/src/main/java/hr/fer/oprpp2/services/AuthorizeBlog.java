package hr.fer.oprpp2.services;

import hr.fer.oprpp2.dao.DAOException;
import hr.fer.oprpp2.model.BlogEntry;
import hr.fer.oprpp2.model.BlogUser;
import hr.fer.oprpp2.router.AuthorSimpleRoute;
import hr.fer.oprpp2.router.RouteMatch;
import hr.fer.oprpp2.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthorizeBlog {
    private final AuthorService authorService = new AuthorService();
    private final BlogService blogService = new BlogService();

    public BlogEntry authorize(BaseServlet self, HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws IOException, ServletException, DAOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        if (userId == null) {
            self.throwError(req, resp, "Not logged in");
            return null;
        }
        Optional<BlogUser> author = this.authorService.getAuthor(match.authorUsername());
        if (author.isEmpty()) {
            self.throwError(req, resp, "User not found");
            return null;
        }
        if (!author.get().getId().equals(userId)) {
            self.throwError(req, resp, "You don't have permissions for this blogpost");
            return null;
        }

        if (!match.authorRoute().equals(AuthorSimpleRoute.AUTHOR_NEW_BLOG)) {
            Optional<BlogEntry> blogEntry = this.blogService.getBlog(match.blogId());
            if (blogEntry.isEmpty()) {
                self.throwError(req, resp, "Blog not found");
                return null;
            }
            if (!blogEntry.get().getUser().getId().equals(userId)) {
                self.throwError(req, resp, "You don't have permissions for this blogpost");
                return null;
            }

            return blogEntry.get();
        }

        return new BlogEntry();
    }
}
