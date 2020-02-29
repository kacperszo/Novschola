package io.novschola.api.v1.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class represents response after correct user's authentication, contains a jwt token
 * @author Kacper Szot
 */
@AllArgsConstructor
@Data
public class JwtResponse {
private String token;
}
