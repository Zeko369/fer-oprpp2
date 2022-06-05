package hr.fer.oprpp2.dao.jpa.impl;

import hr.fer.oprpp2.dao.DAOs.DAOBlogEntry;
import hr.fer.oprpp2.dao.jpa.JPAEMProvider;
import hr.fer.oprpp2.model.BlogEntry;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class JPABlogEntryDAO implements DAOBlogEntry {
    @Override
    public Optional<BlogEntry> getBlogEntry(Long id) {
        try {
            return Optional.ofNullable(JPAEMProvider.getEntityManager().find(BlogEntry.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<BlogEntry> getBlogs(Long userId) {
        return JPAEMProvider.getEntityManager().createQuery("SELECT b FROM BlogEntry b WHERE b.user.id = :userId", BlogEntry.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public BlogEntry savePost(BlogEntry post) {
        JPAEMProvider.getEntityManager().persist(post);
        return post;
    }
}
