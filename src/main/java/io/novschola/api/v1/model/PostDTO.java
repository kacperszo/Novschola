package io.novschola.api.v1.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Long id;
    private UserDTO author;
    private String title;
    private String content;
    private LocalDateTime creationTime;
}
