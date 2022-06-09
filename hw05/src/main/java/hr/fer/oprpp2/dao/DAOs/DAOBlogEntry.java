package hr.fer.oprpp2.dao.DAOs;

import hr.fer.oprpp2.model.BlogComment;
import hr.fer.oprpp2.model.BlogEntry;

import java.util.List;
import java.util.Optional;

/**
 * The interface Dao blog entry.
 *
 * @author franzekan
 */
public interface DAOBlogEntry {
    /**
     * Gets blog entry.
     *
     * @param id the id
     * @return the blog entry
     */
    Optional<BlogEntry> getBlogEntry(Long id);

    /**
     * Gets blogs.
     *
     * @param userId the user id
     * @return the blogs
     */
    List<BlogEntry> getBlogs(Long userId);

    /**
     * Save post blog entry.
     *
     * @param blog the blog
     * @return the blog entry
     */
    BlogEntry savePost(BlogEntry blog);

    /**
     * Save comment.
     *
     * @param setUserEmail the set user email
     */
    void saveComment(BlogComment setUserEmail);
}
