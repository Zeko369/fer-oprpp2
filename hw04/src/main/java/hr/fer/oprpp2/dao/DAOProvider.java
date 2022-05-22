package hr.fer.oprpp2.dao;

import hr.fer.oprpp2.dao.sql.SQLDAO;

public class DAOProvider {

    private static final DAO dao = new SQLDAO();

    public static DAO getDao() {
        return dao;
    }

}
