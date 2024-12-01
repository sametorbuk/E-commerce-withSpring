package com.teknotik.ecommmerce_backend.controller;


import com.teknotik.ecommmerce_backend.Util.JwtUtil;
import com.teknotik.ecommmerce_backend.dto.Headers;
import com.teknotik.ecommmerce_backend.exceptions.EcommerceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class VerificationController {

    private JwtUtil jwtService;

    @Autowired
    public VerificationController(JwtUtil jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity verifyUser(@RequestBody String token) {
        if(!jwtService.isTokenExpired(token)){
            return ResponseEntity.ok(token);
        }else{
           throw  new EcommerceException("This token is expired",HttpStatus.UNAUTHORIZED);
        }
    }
}
