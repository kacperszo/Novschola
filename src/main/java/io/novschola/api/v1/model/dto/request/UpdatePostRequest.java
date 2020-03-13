package io.novschola.api.v1.model.dto.request;

import lombok.Data;
/**
 * Update post request model
 * @author Kacper Szot
 */
@Data
public class UpdatePostRequest {
    private String title;
    private String content;
}
