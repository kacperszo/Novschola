package io.novschola.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Exception class invoked when request is not correct (e.g missing field)
 * returns http code 400
 * @author Kacper Szot
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(Exception e){

    }
    public BadRequestException(){

    }

    public BadRequestException(String e) {
    }
}
