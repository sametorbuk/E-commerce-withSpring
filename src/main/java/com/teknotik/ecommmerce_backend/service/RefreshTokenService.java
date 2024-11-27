package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;

    private UserServiceImpl userService;


    @Autowired
    public RefreshTokenService(UserServiceImpl userService, RefreshTokenRepository refreshTokenRepository) {
        this.userService = userService;
        this.refreshTokenRepository = refreshTokenRepository;
    }



}
