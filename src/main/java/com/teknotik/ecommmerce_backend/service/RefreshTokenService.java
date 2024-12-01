package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.Util.JwtUtil;
import com.teknotik.ecommmerce_backend.entity.RefreshToken;
import com.teknotik.ecommmerce_backend.entity.Role;
import com.teknotik.ecommmerce_backend.entity.User;
import com.teknotik.ecommmerce_backend.repository.RefreshTokenRepository;
import com.teknotik.ecommmerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RefreshTokenService {
    @Value("${jwt.refresh.token.expiration}")
    private long refreshTokenDurationMs;

    private RefreshTokenRepository refreshTokenRepository;
    private UserServiceImpl userService;
    private JwtUtil jwtService;
    private UserRepository userRepository;


    @Autowired
    public RefreshTokenService(UserServiceImpl userService, RefreshTokenRepository refreshTokenRepository, JwtUtil jwtService, UserRepository userRepository) {
        this.userService = userService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService=jwtService;
        this.userRepository=userRepository;
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


    public void deleteByUser(User user){
        refreshTokenRepository.deleteByUser(user);
    }


    public Map<String,String> createNewTokenWithRefresh(String refreshToken){
        User user = refreshTokenRepository.findByToken(refreshToken).get().getUser();
        Set<Role> roles = user.getRoles();

        refreshTokenRepository.deleteByUser(user);

        String newAccessToken = jwtService.generateToken(user.getEmail(),roles);
        RefreshToken newRefreshToken = createRefreshToken(user);

        user.setRefreshToken(newRefreshToken);

        userRepository.save(user);

        Map<String,String> response = new HashMap<>();

        response.put("token" , newAccessToken);
        response.put("refreshToken",newRefreshToken.getToken());
        response.put("email" , user.getEmail());

        return response;


    }



}
