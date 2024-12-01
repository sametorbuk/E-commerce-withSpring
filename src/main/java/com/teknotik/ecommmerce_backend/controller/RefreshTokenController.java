package com.teknotik.ecommmerce_backend.controller;

import com.teknotik.ecommmerce_backend.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RefreshTokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;


    @PostMapping("refresh-token")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String , String> getNewToken(@RequestBody String refreshToken){
      return refreshTokenService.createNewTokenWithRefresh(refreshToken);
    }

}
