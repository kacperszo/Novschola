package io.novschola.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class invoked when provided jwt token is incorrect
 *
 * @author Kacper Szot
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class BadJwtTokenException extends RuntimeException {
    public BadJwtTokenException(Exception e) {
    }
}
