package com.teknotik.ecommmerce_backend.controller;

import com.teknotik.ecommmerce_backend.dto.AddressResponse;
import com.teknotik.ecommmerce_backend.entity.Address;
import com.teknotik.ecommmerce_backend.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/address")
@RequiredArgsConstructor
// prefer feature modules instead of global controller module
// doing centralized controller folder destroy modularization
// ideally you could remove the whole module from your app,
// and nothing breaks (if it's not security or global config module)
public class AddressController {
    private final UserServiceImpl userService;

    @GetMapping
    public List<AddressResponse> findAll(@RequestHeader("Authorization") String token){
        return null;
    }
}