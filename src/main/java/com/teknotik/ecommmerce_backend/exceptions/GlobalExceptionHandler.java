package com.teknotik.ecommmerce_backend.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // it's a very good idea to have one global exception that you control.
    // controlled exception - return 400+
    @ExceptionHandler(EcommerceException.class)
    public ResponseEntity<EcommerceErrorResponse> exceptionHandler(EcommerceException exception) {
        EcommerceErrorResponse response = new EcommerceErrorResponse(System.currentTimeMillis(), exception.getStatus(), exception.getMessage());
        return new ResponseEntity<>(response, exception.getStatus());
    }

    // controlled exception - return 500 +
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<EcommerceErrorResponse> exceptionHandler(Exception exception) {
        // never return unknown messages to the world - it can contain sensitive data (exception.getMessage())
        EcommerceErrorResponse response = new EcommerceErrorResponse(System.currentTimeMillis(), HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // TODO: add third exception handler for 404 case
}
