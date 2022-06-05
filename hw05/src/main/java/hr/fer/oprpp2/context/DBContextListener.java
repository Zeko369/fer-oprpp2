package hr.fer.oprpp2.context;

import hr.fer.oprpp2.dao.jpa.JPAEMFProvider;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DBContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog.db");
        sce.getServletContext().setAttribute("my.application.emf", emf);
        JPAEMFProvider.setEmf(emf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAEMFProvider.setEmf(null);
        EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("my.application.emf");
        if (emf != null) {
            emf.close();
        }
    }
}
