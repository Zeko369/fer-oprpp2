package hr.fer.oprpp2.dao;

import hr.fer.oprpp2.dao.DAOs.DAOBlogEntry;
import hr.fer.oprpp2.dao.DAOs.DAOUser;

public interface DAO {
    DAOBlogEntry blogDao();

    DAOUser userDao();
}
