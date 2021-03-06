package io.novschola.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Comment domain model
 * @author Kacper Szot
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User author;
    @ManyToOne
    private Post post;
    @Lob
    private String content;
    @CreationTimestamp
    private LocalDateTime creationTime;
}
