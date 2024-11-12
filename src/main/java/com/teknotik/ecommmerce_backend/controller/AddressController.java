package com.teknotik.ecommmerce_backend.controller;

import com.teknotik.ecommmerce_backend.dto.AddressResponse;
import com.teknotik.ecommmerce_backend.entity.Address;
import com.teknotik.ecommmerce_backend.service.AddressServiceImpl;
import com.teknotik.ecommmerce_backend.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user/address")
@RequiredArgsConstructor
public class AddressController {

    private AddressServiceImpl addressService;

    @Autowired
    public AddressController(AddressServiceImpl addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public Set<AddressResponse> findAllAddress(@RequestHeader("Authorization") String token) {
        return addressService.findAllAddress(token);
    }

    @PutMapping
    public AddressResponse updateAddress(@RequestHeader("Authorization") String token, @RequestBody Address address) {
        return addressService.updateAddress(token, address);
    }

    @PostMapping
    public AddressResponse saveAddress(@RequestHeader("Authorization") String token, Address address) {
        return addressService.saveAddress(token, address);
    }

    @DeleteMapping("/{id}")
    public AddressResponse deleteAddress(@RequestHeader("Authorization") String token, @PathVariable long id) {
        return addressService.deleteAddress(token, id);
    }

}