package hr.fer.oprpp2.dao;

import hr.fer.oprpp2.model.BlogEntry;

import java.util.List;

public interface DAO {
    /**
     * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
     * vraća <code>null</code>.
     *
     * @param id ključ zapisa
     * @return entry ili <code>null</code> ako entry ne postoji
     * @throws DAOException ako dođe do pogreške pri dohvatu podataka
     */
    BlogEntry getBlogEntry(Long id) throws DAOException;

    List<BlogEntry> getBlogs() throws DAOException;
}
