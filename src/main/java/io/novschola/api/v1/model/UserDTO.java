package io.novschola.api.v1.model;

import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private String schoolClass;
}
