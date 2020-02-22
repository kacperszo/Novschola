package io.novschola.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Post business model class
 * @author Kacper Szot
 */
@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User author;
    private String title;
    @Lob
    private String content;
    @CreationTimestamp
    private LocalDateTime creationTime;
}
