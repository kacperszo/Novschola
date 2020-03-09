package io.novschola.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class invoked when request is not Forbidden
 * returns http code 403
 *
 * @author Kacper Szot
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String e) {
        super(e);
    }
}
