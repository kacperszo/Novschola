package io.novschola.api.v1.model;

import lombok.Data;

/**
 * Class represents a single request performed to authenticate and get a jwt token
 * @author Kacper Szot
 */
@Data
public class JwtRequest {
    private String email;
    private String password;
}
