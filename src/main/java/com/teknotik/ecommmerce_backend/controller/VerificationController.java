package com.teknotik.ecommmerce_backend.controller;


import com.teknotik.ecommmerce_backend.Util.JwtUtil;
import com.teknotik.ecommmerce_backend.dto.Headers;
import com.teknotik.ecommmerce_backend.exceptions.EcommerceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class VerificationController {

    private JwtUtil jwtService;

    @Autowired
    public VerificationController(JwtUtil jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/verify")
    public ResponseEntity verifyUser(@RequestHeader("Authorization") String token) {
        if(!jwtService.isTokenExpired(token)){
            Map<String ,String> response = new HashMap<>();
            response.put("token" ,token.replaceFirst("^Bearer\\s+", ""));
            return ResponseEntity.ok(response);
        }else{
           throw  new EcommerceException("This token is expired",HttpStatus.UNAUTHORIZED);
        }
    }
}
