package com.teknotik.ecommmerce_backend.controller;


import com.teknotik.ecommmerce_backend.dto.RegisterResponse;
import com.teknotik.ecommmerce_backend.dto.RegistrationUser;
import com.teknotik.ecommmerce_backend.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationService authenticationService;


    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegistrationUser registrationUser) {
        switch (registrationUser.roleId()) {
            case 1:
                authenticationService.adminRegister(registrationUser.name(), registrationUser.email(), registrationUser.password());
                return new RegisterResponse(registrationUser.email(), "Registration happened successfully");
            case 2:

            case 3:
                authenticationService.customerRegister(registrationUser.name(), registrationUser.email(), registrationUser.password());
                return new RegisterResponse(registrationUser.email(), "Registration happened successfully");
        }
        throw new RuntimeException();
    }
}
