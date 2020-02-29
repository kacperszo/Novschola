package io.novschola.api.v1.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * The class represents a single request made to update user
 * @author Kacper Szot
 */
@Data
public class UpdateUserRequest {
    @NotBlank(message = "firstName cannot not be blank")
    @NotNull(message = "firstName cannot be null")
    @Size(max = 50, message = "firstName length must be lower then 50")
    String firstName;

    @NotBlank(message = "lastName cannot be blank")
    @NotNull(message = "lastName cannot be null")
    @Size(max = 50, message = "lastName length must be lower then 50")
    String lastName;

    @NotNull(message = "bio cannot be blank")
    String bio;
}
