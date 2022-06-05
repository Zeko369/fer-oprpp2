package hr.fer.oprpp2.dao.DAOs;

import hr.fer.oprpp2.model.BlogComment;
import hr.fer.oprpp2.model.BlogEntry;

import java.util.List;
import java.util.Optional;

public interface DAOBlogEntry {
    Optional<BlogEntry> getBlogEntry(Long id);

    List<BlogEntry> getBlogs(Long userId);

    BlogEntry savePost(BlogEntry blog);

    void saveComment(BlogComment setUserEmail);
}
