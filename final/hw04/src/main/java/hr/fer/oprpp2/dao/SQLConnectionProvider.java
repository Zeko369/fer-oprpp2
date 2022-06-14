package hr.fer.oprpp2.dao;

import java.sql.Connection;

/**
 * The type Sql connection provider.
 *
 * @author franzekan
 */
public class SQLConnectionProvider {
    private static final ThreadLocal<Connection> connections = new ThreadLocal<>();

    /**
     * Sets connection.
     *
     * @param con the con
     */
    public static void setConnection(Connection con) {
        if (con == null) {
            connections.remove();
        } else {
            connections.set(con);
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public static Connection getConnection() {
        return connections.get();
    }

}
