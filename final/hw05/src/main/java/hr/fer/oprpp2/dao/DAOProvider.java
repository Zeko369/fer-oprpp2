package hr.fer.oprpp2.dao;

import hr.fer.oprpp2.dao.jpa.JPADAOImpl;

public class DAOProvider {
    private static final DAO dao = new JPADAOImpl();

    public static DAO getDAO() {
        return dao;
    }
}
