package hr.fer.oprpp2.dao;

import hr.fer.oprpp2.dao.sql.SQLDAO;

/**
 * The type Dao provider.
 *
 * @author franzekan
 */
public class DAOProvider {

    private static final DAO dao = new SQLDAO();

    /**
     * Gets dao.
     *
     * @return the dao
     */
    public static DAO getDao() {
        return dao;
    }

}
