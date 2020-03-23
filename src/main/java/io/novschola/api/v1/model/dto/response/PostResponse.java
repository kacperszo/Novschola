package io.novschola.api.v1.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Post data transfer object class
 * Objects of this class are returned by controllers instead of business' layer models
 * @author Kacper Szot
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private UserResponse author;
    private String title;
    private String content;
    private LocalDateTime creationTime;
}
