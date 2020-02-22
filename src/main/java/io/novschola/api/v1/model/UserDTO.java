package io.novschola.api.v1.model;

import lombok.Data;

/**
 * User data transfer object
 * Objects of this class are returned by controllers instead of business' layer models
 * @author Kacper Szot
 */
@Data
public class UserDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private SchoolClassDTO schoolClass;
}
