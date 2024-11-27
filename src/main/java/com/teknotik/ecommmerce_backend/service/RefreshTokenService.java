package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.entity.RefreshToken;
import com.teknotik.ecommmerce_backend.entity.User;
import com.teknotik.ecommmerce_backend.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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


    public RefreshToken createRefreshToken(User user){
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(LocalDateTime.now().plusDays(refreshTokenDurationMs));

        return refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }



}
