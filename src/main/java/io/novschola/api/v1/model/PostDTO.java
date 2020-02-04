package io.novschola.api.v1.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime creationTime;
}
