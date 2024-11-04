package com.teknotik.ecommmerce_backend.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<EcommerceErrorResponse> exceptionHandler(EcommerceException exception){
        EcommerceErrorResponse response=new EcommerceErrorResponse(System.currentTimeMillis() , exception.getStatus(),exception.getMessage() );
        return new ResponseEntity<>(response,exception.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<EcommerceErrorResponse> exceptionHandler(Exception exception){
        EcommerceErrorResponse response=new EcommerceErrorResponse(System.currentTimeMillis(), HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
