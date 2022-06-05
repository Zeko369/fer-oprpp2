package hr.fer.oprpp2.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity(name = "blogEntry")
@Table(name = "blog_comments")
public class BlogComment {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "blog_entry_id")
    private BlogEntry blogEntry;

    @Column(length = 100, nullable = false, name = "user_email")
    private String userEmail;

    @Column(length = 4096, nullable = false)
    private String message;

    @Column(nullable = false, name = "commented_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentedAt;
}
