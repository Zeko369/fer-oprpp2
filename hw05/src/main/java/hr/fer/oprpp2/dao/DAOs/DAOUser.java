package hr.fer.oprpp2.dao.DAOs;

import hr.fer.oprpp2.model.BlogUser;

import java.util.List;
import java.util.Optional;

/**
 * The interface Dao user.
 *
 * @author franzekan
 */
public interface DAOUser {
    /**
     * Gets user by username.
     *
     * @param username the username
     * @return the user by username
     */
    Optional<BlogUser> getUserByUsername(String username);

    /**
     * Gets user by email.
     *
     * @param email the email
     * @return the user by email
     */
    Optional<BlogUser> getUserByEmail(String email);

    /**
     * Save user.
     *
     * @param user the user
     */
    void saveUser(BlogUser user);

    /**
     * Gets all users.
     *
     * @return the all users
     */
    List<BlogUser> getAllUsers();
}
