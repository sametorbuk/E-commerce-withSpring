package com.teknotik.ecommmerce_backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EcommerceException extends RuntimeException{
    private HttpStatus status;

    public EcommerceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
