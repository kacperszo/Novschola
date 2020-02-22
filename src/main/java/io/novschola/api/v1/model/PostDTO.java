package io.novschola.api.v1.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Post data transfer object class
 * Objects of this class are returned by controllers instead of business' layer models
 * @author Kacper Szot
 */
@Data
public class PostDTO {
    private Long id;
    private UserDTO author;
    private String title;
    private String content;
    private LocalDateTime creationTime;
}
