package com.teknotik.ecommmerce_backend.dto;

import org.springframework.stereotype.Component;


public record RegistrationUser(String name , String email , String password , int roleId) {
}


