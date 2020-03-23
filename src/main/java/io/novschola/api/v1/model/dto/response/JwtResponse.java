package io.novschola.api.v1.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class represents response after correct user's authentication, contains a jwt token
 * @author Kacper Szot
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
private String token;
}
