package com.teknotik.ecommmerce_backend.controller;

import com.teknotik.ecommmerce_backend.dto.AddressResponse;
import com.teknotik.ecommmerce_backend.dto.CardResponse;
import com.teknotik.ecommmerce_backend.entity.Address;
import com.teknotik.ecommmerce_backend.entity.CreditCard;
import com.teknotik.ecommmerce_backend.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/address")
    public Set<AddressResponse> findAllAddress(@RequestHeader("Authorization")String token){
        return userService.findAllAddress(token);
    }

    @PutMapping("/address")
    public AddressResponse updateAddress(@RequestHeader("Authorization")String token , @RequestBody Address address){
        return userService.updateAddress(token,address);
    }

    @PostMapping("/address")
    public AddressResponse saveAddress(@RequestHeader("Authorization")String token , Address address){
        return userService.saveAddress(token , address);
    }

    @DeleteMapping("/address/{id}")
    public AddressResponse deleteAddress(@RequestHeader("Authorization")String token,@PathVariable long id){
        return userService.deleteAddress(token,id);
    }


    @GetMapping("/card")
    public Set<CardResponse> findAllCard(@RequestHeader("Authorization")String token){
        return userService.findAllCard(token);
    }

    @PostMapping("/card")
    public CardResponse saveCard(@RequestHeader("Authorization")String token, @RequestBody CreditCard creditCard){
        return userService.saveCard(token,creditCard);
    }

    @PutMapping("/card")
    public CardResponse updateCard(@RequestHeader("Authorization") String token,@RequestBody CreditCard creditCard){
        return userService.updateCard(token, creditCard);
    }

    @DeleteMapping("/card/{id}")
    public CardResponse deleteCard(@RequestHeader("Authorization") String token,@PathVariable long id){
        return userService.deleteCard(token, id);
    }




}
