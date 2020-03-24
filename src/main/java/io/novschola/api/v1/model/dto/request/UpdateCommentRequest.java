package io.novschola.api.v1.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * The class represents a single request made to update a existing comment
 * @author Kacper Szot
 */
@Data
public class UpdateCommentRequest {
    @NotNull(message = "content of the comment must not be null")
    @NotBlank(message = "content of the comment must not be blank")
    private String content;
}
