package io.novschola.api.v1.model.dto.request;

import lombok.Data;

/**
 * Create post request model
 * @author Kacper Szot
 */
@Data
public class CreatePostRequest {
    private String title;
    private String content;
}
