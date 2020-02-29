package io.novschola.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

/**
 * Exception class invoked when request is not correct (e.g missing field)
 * returns http code 400
 *
 * @author Kacper Szot
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(Exception e) {
        super(e);
    }

    public BadRequestException() {

    }

    public BadRequestException(String e) {
        super(e);
    }

    public BadRequestException(BindingResult result) {
        super(
                result
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"))
        );


    }
}
