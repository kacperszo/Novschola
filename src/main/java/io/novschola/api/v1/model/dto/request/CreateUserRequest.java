package io.novschola.api.v1.model.dto.request;

import lombok.Data;

import javax.validation.constraints.*;
/**
 * The class represents a single request made to create a new user
 * @author Kacper Szot
 */
@Data
public class CreateUserRequest {

@NotNull(message = "email cannot be null")
@Email(message = "Provide valid email address")
String email;

@NotBlank(message = "firstName cannot not be blank")
@Size(max = 50, message = "firstName length must be lower then 50")
@NotNull(message = "firstName cannot be null")
String firstName;

@NotBlank(message = "lastName cannot be null")
@Size(max = 50, message = "lastName length must be lower then 50")
@NotNull(message = "lastName cannot be blank")
String lastName;


@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,150}$", message = "Password must be 8 to 150 character long, password requires numbers and both lowercase and uppercase letters")
@NotNull(message = "Password cannot be null")
String password;
}
