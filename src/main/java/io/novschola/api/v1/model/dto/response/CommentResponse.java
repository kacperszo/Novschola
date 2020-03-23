package io.novschola.api.v1.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Comment DTO for response
 * Objects of this class are returned by controllers instead of business' layer models
 *
 * @author Kacper Szot
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private UserResponse author;
    private PostResponse post;
    private String content;
    private LocalDateTime creationTime;
}
