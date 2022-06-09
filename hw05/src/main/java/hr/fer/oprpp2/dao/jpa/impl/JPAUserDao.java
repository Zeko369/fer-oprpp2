package hr.fer.oprpp2.dao.jpa.impl;

import hr.fer.oprpp2.dao.DAOs.DAOUser;
import hr.fer.oprpp2.dao.jpa.JPAEMProvider;
import hr.fer.oprpp2.model.BlogUser;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class JPAUserDao implements DAOUser {
    private final List<String> allowedFields = List.of("username", "email");

    private Optional<BlogUser> getUserByField(String field, String value) {
        if (!allowedFields.contains(field)) {
            throw new RuntimeException("Invalid field name: " + field);
        }

        try {
            BlogUser user = JPAEMProvider.getEntityManager()
                    .createQuery(String.format("SELECT u FROM BlogUser u WHERE u.%s = :%s", field, field), BlogUser.class)
                    .setParameter(field, value)
                    .getSingleResult();

            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BlogUser> getUserByUsername(String username) {
        return this.getUserByField("username", username);
    }

    @Override
    public Optional<BlogUser> getUserByEmail(String email) {
        return this.getUserByField("email", email);
    }

    @Override
    public void saveUser(BlogUser user) {
        JPAEMProvider.getEntityManager().persist(user);
    }

    @Override
    public List<BlogUser> getAllUsers() {
        return JPAEMProvider.getEntityManager()
                .createQuery("SELECT u FROM BlogUser u", BlogUser.class)
                .getResultList();
    }
}
