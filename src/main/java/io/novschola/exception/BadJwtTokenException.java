package io.novschola.exception;
/**
 * Exception class invoked when provided jwt token is incorrect
 * @author Kacper Szot
 */
public class BadJwtTokenException extends RuntimeException {
    public BadJwtTokenException(Exception e) {
    }
}
