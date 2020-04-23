package io.novschola.api.v1.model.dto.request;

import lombok.Data;

/**
 * Class represents a single request performed to authenticate and get a jwt token
 * @author Kacper Szot
 */
@Data
public class AuthRequest {
    private String email;
    private String password;
}
