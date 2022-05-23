package hr.fer.oprpp2.listener;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import hr.fer.oprpp2.dao.sql.Seed;
import hr.fer.oprpp2.util.LoadProperties;

/**
 * The type Init listener.
 *
 * @author franzekan
 */
@WebListener
public class InitListener implements ServletContextListener {
    private final static String DB_SETTING_PATH = "/WEB-INF/dbsettings.properties";
    /**
     * The constant DB_CONNECTION_ATTRIBUTE.
     */
    public final static String DB_CONNECTION_ATTRIBUTE = "db";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = LoadProperties.load(sce.getServletContext().getRealPath(DB_SETTING_PATH));

        if (!properties.containsKey("host")
                || !properties.containsKey("port")
                || !properties.containsKey("name")
                || !properties.containsKey("user")
                || !properties.containsKey("password")
        ) {
            throw new RuntimeException("Database settings not complete.");
        }

        String dbURL = String.format("jdbc:postgresql://%s/%s", properties.getProperty("host"), properties.getProperty("name"));
        ComboPooledDataSource dbPool = new ComboPooledDataSource();
        try {
            dbPool.setDriverClass("org.postgresql.Driver");
        } catch (PropertyVetoException e1) {
            throw new RuntimeException("Error initializing pool", e1);
        }
        dbPool.setJdbcUrl(dbURL);
        dbPool.setUser(properties.getProperty("user"));
        dbPool.setPassword(properties.getProperty("password"));

        try {
            Connection connection = dbPool.getConnection();
            Seed seed = new Seed(connection);
            if (!seed.hasAllTables()) {
                seed.createTables();
            }
            if (!seed.hasData()) {
                seed.generatePolls();
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sce.getServletContext().setAttribute(DB_CONNECTION_ATTRIBUTE, dbPool);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext().getAttribute(DB_CONNECTION_ATTRIBUTE);
        if (cpds != null) {
            try {
                DataSources.destroy(cpds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
