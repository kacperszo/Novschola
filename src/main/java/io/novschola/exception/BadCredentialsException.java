package io.novschola.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class invoked when provided credentials are incorrect
 * returns http code 401
 * @author Kacper Szot
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class BadCredentialsException extends RuntimeException{
}
