package hr.fer.oprpp2.dao;

import hr.fer.oprpp2.dao.DAOs.DAOBlogEntry;
import hr.fer.oprpp2.dao.DAOs.DAOUser;

/**
 * The interface Dao.
 *
 * @author franzekan
 */
public interface DAO {
    /**
     * Blog dao dao blog entry.
     *
     * @return the dao blog entry
     */
    DAOBlogEntry blogDao();

    /**
     * User dao dao user.
     *
     * @return the dao user
     */
    DAOUser userDao();
}
