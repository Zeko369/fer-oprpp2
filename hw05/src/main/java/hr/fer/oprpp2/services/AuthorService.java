package hr.fer.oprpp2.services;

import hr.fer.oprpp2.dao.DAOProvider;
import hr.fer.oprpp2.model.BlogUser;
import hr.fer.oprpp2.model.BlogUserComment;

import java.util.List;
import java.util.Optional;

public class AuthorService {
    public List<BlogUser> getAuthors() {
        return DAOProvider.getDAO().userDao().getAllUsers();
    }

    public Optional<BlogUser> getAuthor(String username) {
        return DAOProvider.getDAO().userDao().getUserByUsername(username);
    }

    public void commentAuthor(BlogUser author, BlogUser commenter, String comment) {
        DAOProvider.getDAO().userDao().saveComment(new BlogUserComment().setComment(comment).setCommenter(commenter).setUser(author));
    }
}
