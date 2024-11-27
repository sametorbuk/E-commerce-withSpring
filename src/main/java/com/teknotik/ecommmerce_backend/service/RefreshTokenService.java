package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    @Value("${jwt.refresh.token.expiration}")
    private long refreshTokenDurationMs;

    private RefreshTokenRepository refreshTokenRepository;

    private UserServiceImpl userService;


    @Autowired
    public RefreshTokenService(UserServiceImpl userService, RefreshTokenRepository refreshTokenRepository) {
        this.userService = userService;
        this.refreshTokenRepository = refreshTokenRepository;
    }



}
