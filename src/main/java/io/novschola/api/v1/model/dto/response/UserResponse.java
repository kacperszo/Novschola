package io.novschola.api.v1.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User data transfer object
 * Objects of this class are returned by controllers instead of business' layer models
 * @author Kacper Szot
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private SchoolClassResponse schoolClass;
}
