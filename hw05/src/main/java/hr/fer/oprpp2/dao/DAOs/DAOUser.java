package hr.fer.oprpp2.dao.DAOs;

import hr.fer.oprpp2.model.BlogUser;

import java.util.List;
import java.util.Optional;

public interface DAOUser {
    Optional<BlogUser> getUserByUsername(String username);

    Optional<BlogUser> getUserByEmail(String email);

    void saveUser(BlogUser user);

    List<BlogUser> getAllUsers();
}
