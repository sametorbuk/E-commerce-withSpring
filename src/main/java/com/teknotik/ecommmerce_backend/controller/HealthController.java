package com.teknotik.ecommmerce_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    // in reality, you should use actuator for that
    // just for testing (check resources/index.html)
    @GetMapping
    ResponseEntity<String> getHealthCheck() {
        return ResponseEntity.ok().body("ok");
    }

}
