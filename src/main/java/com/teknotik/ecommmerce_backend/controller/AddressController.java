package com.teknotik.ecommmerce_backend.controller;

import com.teknotik.ecommmerce_backend.dto.AddressResponse;
import com.teknotik.ecommmerce_backend.entity.Address;
import com.teknotik.ecommmerce_backend.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/address")
public class AddressController {

    private UserServiceImpl userService;

    @Autowired
    public AddressController(UserServiceImpl userService) {
        this.userService = userService;
    }



    @GetMapping
    public List<AddressResponse> findAll(@RequestHeader("Authorization")String token){

    }
}

