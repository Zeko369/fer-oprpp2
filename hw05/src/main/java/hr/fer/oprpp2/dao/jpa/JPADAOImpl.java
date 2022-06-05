package hr.fer.oprpp2.dao.jpa;

import hr.fer.oprpp2.dao.DAO;
import hr.fer.oprpp2.dao.DAOException;
import hr.fer.oprpp2.model.BlogEntry;

import java.util.List;

public class JPADAOImpl implements DAO {
    @Override
    public BlogEntry getBlogEntry(Long id) {
        return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
    }

    @Override
    public List<BlogEntry> getBlogs() {
        return JPAEMProvider.getEntityManager().createQuery("SELECT b FROM BlogEntry b", BlogEntry.class).getResultList();
    }
}
