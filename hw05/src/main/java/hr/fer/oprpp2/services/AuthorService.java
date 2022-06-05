package hr.fer.oprpp2.services;

import hr.fer.oprpp2.dao.DAOException;
import hr.fer.oprpp2.dao.DAOProvider;
import hr.fer.oprpp2.model.BlogUser;

import java.util.List;
import java.util.Optional;

public class AuthorService {
    public List<BlogUser> getAuthors() throws DAOException {
        return DAOProvider.getDAO().userDao().getAllUsers();
    }

    public Optional<BlogUser> getAuthor(String username) throws DAOException {
        return DAOProvider.getDAO().userDao().getUserByUsername(username);
    }
}
