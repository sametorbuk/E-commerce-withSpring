package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.entity.User;
import com.teknotik.ecommmerce_backend.exceptions.EcommerceException;
import com.teknotik.ecommmerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User>  foundUser =userRepository.findByEmail(email);
        if(foundUser.isPresent()){
            return foundUser;
        }

        throw new EcommerceException("There is no user with this email" + email , HttpStatus.NOT_FOUND);
    }
}
