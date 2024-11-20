package com.teknotik.ecommmerce_backend.controller;

import com.teknotik.ecommmerce_backend.dto.AddressResponse;
import com.teknotik.ecommmerce_backend.entity.Address;
import com.teknotik.ecommmerce_backend.service.AddressServiceImpl;
import com.teknotik.ecommmerce_backend.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Secured({"ROLE_admin", "ROLE_customer"})
    @GetMapping
    public Set<AddressResponse> findAllAddress(@RequestHeader("Authorization") String token) {
        return addressService.findAllAddress(token);
    }

    @Secured({"ROLE_admin", "ROLE_customer"})
    @PutMapping
    public AddressResponse updateAddress(@RequestHeader("Authorization") String token, @RequestBody Address address) {
        return addressService.updateAddress(token, address);
    }

    @Secured({"ROLE_admin", "ROLE_customer"})
    @PostMapping
    public AddressResponse saveAddress(@RequestHeader("Authorization") String token,@RequestBody Address address) {
        return addressService.saveAddress(token, address);
    }

    @Secured({"ROLE_admin", "ROLE_customer"})
    @DeleteMapping("/{id}")
    public AddressResponse deleteAddress(@RequestHeader("Authorization") String token, @PathVariable long id) {
        return addressService.deleteAddress(token, id);
    }

}