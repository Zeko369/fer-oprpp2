package hr.fer.oprpp2.dao.jpa;

import hr.fer.oprpp2.dao.DAO;
import hr.fer.oprpp2.dao.DAOs.DAOBlogEntry;
import hr.fer.oprpp2.dao.DAOs.DAOUser;
import hr.fer.oprpp2.dao.jpa.impl.JPABlogEntryDAO;
import hr.fer.oprpp2.dao.jpa.impl.JPAUserDao;

public class JPADAOImpl implements DAO {
    private final DAOBlogEntry blogEntryDAO = new JPABlogEntryDAO();
    private final DAOUser userDAO = new JPAUserDao();

    @Override
    public DAOBlogEntry blogDao() {
        return this.blogEntryDAO;
    }

    @Override
    public DAOUser userDao() {
        return this.userDAO;
    }
}
