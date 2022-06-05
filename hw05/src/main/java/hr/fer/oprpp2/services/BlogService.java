package hr.fer.oprpp2.services;

import hr.fer.oprpp2.dao.DAOException;
import hr.fer.oprpp2.dao.DAOProvider;
import hr.fer.oprpp2.model.BlogEntry;
import hr.fer.oprpp2.model.BlogUser;

import java.util.List;
import java.util.Optional;

public class BlogService {
    public List<BlogEntry> getBlogsForUser(Long userId) throws DAOException {
        return DAOProvider.getDAO().blogDao().getBlogs(userId);
    }

    public Optional<BlogEntry> getBlog(Long blogId) throws DAOException {
        return DAOProvider.getDAO().blogDao().getBlogEntry(blogId);
    }

    public BlogEntry createBlog(BlogUser user, String title, String body) {
        return DAOProvider.getDAO().blogDao().savePost(new BlogEntry().setTitle(title).setBody(body).setUser(user));
    }

    public BlogEntry updateBlog(BlogEntry blog, String title, String body) {
        return DAOProvider.getDAO().blogDao().savePost(blog.setTitle(title).setBody(body));
    }
}
