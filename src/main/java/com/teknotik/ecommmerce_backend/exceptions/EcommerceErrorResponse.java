package com.teknotik.ecommmerce_backend.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
public class EcommerceErrorResponse {
    private int status;
    private String message;
    private long timeStamps;


    public EcommerceErrorResponse(long timeStamps, HttpStatus status, String message) {
        this.timeStamps = timeStamps;
        this.status = status.value();
        this.message = message;
    }
}
