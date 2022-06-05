package hr.fer.oprpp2.services;

import hr.fer.oprpp2.dao.DAOException;
import hr.fer.oprpp2.dao.DAOProvider;
import hr.fer.oprpp2.model.BlogEntry;

import java.util.List;
import java.util.Optional;

public class BlogService {
    public List<BlogEntry> getBlogsForUser(Long userId) throws DAOException {
        return DAOProvider.getDAO().blogDao().getBlogs(userId);
    }

    public Optional<BlogEntry> getBlog(Long blogId) throws DAOException {
        return DAOProvider.getDAO().blogDao().getBlogEntry(blogId);
    }

    public BlogEntry createBlog(int userId, String title, String content) {
        return null;
    }

    public BlogEntry updateBlog(int blogId, String title, String content) {
        return null;
    }
}
