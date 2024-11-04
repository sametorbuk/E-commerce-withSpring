package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
}
