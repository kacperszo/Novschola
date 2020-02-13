package io.novschola.api.v1.model;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}
