package com.teknotik.ecommmerce_backend.controller;


import com.teknotik.ecommmerce_backend.Util.JwtUtil;
import com.teknotik.ecommmerce_backend.dto.Headers;
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
    public ResponseEntity verifyUser(@RequestBody Headers headers) {
        if(!jwtService.isTokenExpired(headers.authorization())){
            return ResponseEntity.ok(headers.authorization());
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz token");
        }
    }
}
