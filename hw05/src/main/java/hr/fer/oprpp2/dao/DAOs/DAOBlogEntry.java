package hr.fer.oprpp2.dao.DAOs;

import hr.fer.oprpp2.dao.DAOException;
import hr.fer.oprpp2.model.BlogComment;
import hr.fer.oprpp2.model.BlogEntry;

import java.util.List;
import java.util.Optional;

public interface DAOBlogEntry {
    /**
     * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
     * vraća <code>null</code>.
     *
     * @param id ključ zapisa
     * @return entry ili <code>null</code> ako entry ne postoji
     * @throws DAOException ako dođe do pogreške pri dohvatu podataka
     */
    Optional<BlogEntry> getBlogEntry(Long id) throws DAOException;

    List<BlogEntry> getBlogs(Long userId) throws DAOException;

    BlogEntry savePost(BlogEntry blog);

    void saveComment(BlogComment setUserEmail);
}
