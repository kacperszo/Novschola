package io.novschola.api.v1.model;

import io.novschola.model.SchoolClass;
import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private Long schoolClassId;
}
