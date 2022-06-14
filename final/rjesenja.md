# Zavrsni ispit

## Zadatak 1 (hw04)

- Dodao sam jos jedan stupac u tablicu poll_options, te renamao `votes` -> `likes`

- Glavne promjene su bile dodati provjeri jel u queryParamu ima `dislike=true` i ako da, onda poslati dislike boolean kao true

- Client side sam linkove sa imenima zamijenimo sa 2 LIKE i DISLIKE gumba

```java src/main/java/hr/fer/oprpp2/dao/DAO.java
public interface DAO {
    *
    * @param optionId the option id
    */
    void vote(Integer optionId, boolean dislike);
}
```

```java SQLDao.java
@Override
public void vote(Integer optionId, boolean dislike) {
    Connection con = SQLConnectionProvider.getConnection();
    PreparedStatement pst;

    try {
        if (dislike) {
            pst = con.prepareStatement("update poll_options set dislikes = dislikes + 1 where id = ?");
        } else {
            pst = con.prepareStatement("update poll_options set likes = likes + 1 where id = ?");
        }
        pst.setInt(1, optionId);

        try {
            pst.executeUpdate();
        } finally {
            try {
                pst.close();
            } catch (Exception ignored) {
            }
        }
    } catch (Exception ex) {
        throw new DAOException("Error voting", ex);
    }
}
```

```java vote servlet (glavna izmjena je dodavanje boolean dislike)
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String voteIdRaw = req.getParameter("id");
    if (voteIdRaw == null) {
        this.throwError(req, resp, "Option not selected");
        return;
    }

    int optionId;
    try {
        optionId = Integer.parseInt(voteIdRaw);
    } catch (NumberFormatException e) {
        this.throwError(req, resp, "OptionID is not an integer");
        return;
    }

    boolean dislike = req.getParameter("dislike") != null && req.getParameter("dislike").equals("true");

    DAO dao = DAOProvider.getDao();
    dao.vote(optionId, dislike);

    resp.sendRedirect("/webapp1/voting/results?pollId=" + req.getParameter("pollId"));
}
```

## Zadatak 2 (hw05)

- Dodan novi entitet BlogUserComment koji je spojen na usera 2 puta (jednom kao author, jednom kao komentator)

- Promijenio template da ovisno o tome tko je na pageu renderira nista / formu / listu komentara

- U userDao dodao spremanje BlogUserComment

```java userDao
void saveComment(BlogUserComment userComment);
```

```java BlogUserComment
package hr.fer.oprpp2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "blog_user_comments")
@Cacheable()
public class BlogUserComment {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 200, nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private BlogUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id", nullable = false)
    private BlogUser commenter;

    @Column(length = 4096, nullable = false, name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
```

```java u BlogUser entitet dodano
@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
@OrderBy("createdAt ASC")
public List<BlogUserComment> myComments;

@OneToMany(mappedBy = "commenter", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
@OrderBy("createdAt ASC")
public List<BlogUserComment> commentsOnOtherUsers;
```

```java u AuthorService dodano
public void commentAuthor(BlogUser author, BlogUser commenter, String comment) {
    DAOProvider.getDAO().userDao().saveComment(new BlogUserComment().setComment(comment).setCommenter(commenter).setUser(author));
}
```

```java u AuthorServlet dodano
// Matcher u doPost()
switch (match.authorRoute()) {
    case AUTHOR -> this.commentAuthor(req, resp, match);
    case AUTHOR_EDIT_BLOG -> this.updateBlog(req, resp, match);
    case AUTHOR_NEW_BLOG -> this.createBlog(req, resp, match);
    case AUTHOR_COMMENT_BLOG -> this.commentBlog(req, resp, match);
}

// commentAuthor methoda
private void commentAuthor(HttpServletRequest req, HttpServletResponse resp, RouteMatch match) throws ServletException, DAOException, IOException {
    Optional<BlogUser> author = this.authorService.getAuthor(match.authorUsername());
    if (author.isEmpty()) {
        this.throwError(req, resp, "Author not found!");
        return;
    }

    String comment = req.getParameter("comment");
    if (comment == null || comment.isEmpty()) {
        this.throwError(req, resp, "Comment is empty!");
        return;
    }

    String commenterUsername = (String) req.getSession().getAttribute("username");
    Optional<BlogUser> commenter = this.authorService.getAuthor(commenterUsername);
    if (commenter.isEmpty()) {
        this.throwError(req, resp, "Commenter not found!");
        return;
    }

    this.authorService.commentAuthor(author.get(), commenter.get(), comment);
    resp.sendRedirect("/blog-app/servlet/author/" + author.get().getUsername());
}
```
