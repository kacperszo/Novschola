package io.novschola.api.v1.model.dto.request;

import lombok.Data;

import javax.validation.constraints.*;
/**
 * The class represents a single request made to create a new user
 * @author Kacper Szot
 */
@Data
public class CreateUserRequest {
@Email(message = "Provide valid email address")
String email;
@NotBlank(message = "firstName cannot not be blank")
@Size(max = 50, message = "firstName length must be lower then 50")
String firstName;
@NotBlank(message = "lastName cannot be blank")
@Size(max = 50, message = "lastName length must be lower then 50")
String lastName;
@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,150}$", message = "Password must be 8 to 150 character long, password requires numbers and both lowercase and uppercase letters")
String password;
}
