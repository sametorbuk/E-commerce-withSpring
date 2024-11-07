package com.teknotik.ecommmerce_backend.dto;

import org.springframework.stereotype.Component;

@Component
public record RegistrationUser(String name , String email , String password , int roleId) {
}


