package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.entity.Role;
import com.teknotik.ecommmerce_backend.entity.Store;
import com.teknotik.ecommmerce_backend.entity.User;
import com.teknotik.ecommmerce_backend.exceptions.EcommerceException;
import com.teknotik.ecommmerce_backend.repository.RoleRepository;
import com.teknotik.ecommmerce_backend.repository.StoreRepository;
import com.teknotik.ecommmerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private StoreRepository storeRepository;


    @Autowired
    public AuthenticationService(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository , StoreRepository storeRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.storeRepository=storeRepository;
    }


    public User customerRegister(String name,String email , String password){
        Optional<User> foundUser=userRepository.findByEmail(email);
        if(foundUser.isPresent()){
            throw new EcommerceException("This user already exist in system" , HttpStatus.BAD_REQUEST);
        }

        String encodedPassword=passwordEncoder.encode(password);

        Optional<Role> findRole=roleRepository.findByAuthority("customer");

        Set<Role> roles = new HashSet<>();
        roles.add(findRole.get());


        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(encodedPassword);
        newUser.setRoles(roles);

        return userRepository.save(newUser);

    }



    public User adminRegister(String name,String email , String password){
        Optional<User> foundUser=userRepository.findByEmail(email);
        if(foundUser.isPresent()){
            throw new EcommerceException("This user already exist in system" , HttpStatus.BAD_REQUEST);
        }

        String encodedPassword=passwordEncoder.encode(password);

        Optional<Role> findRole=roleRepository.findByAuthority("admin");

        Set<Role> roles = new HashSet<>();
        roles.add(findRole.get());


        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(encodedPassword);
        newUser.setRoles(roles);

        return userRepository.save(newUser);

    }


    public Store storeRegister(String name, String email , String password,String storeName,String storePhone,String storeTaxId, String storeBankAccount){
        Optional<Store> foundUser=storeRepository.findByEmail(email);
        if(foundUser.isPresent()){
            throw new EcommerceException("This store already exist in system" , HttpStatus.BAD_REQUEST);
        }

        String encodedPassword=passwordEncoder.encode(password);


        Store newStore = new Store();
        newStore.setName(name);
        newStore.setEmail(email);
        newStore.setPassword(encodedPassword);
        newStore.setStoreBankAccount(storeBankAccount);
        newStore.setStoreTaxId(storeTaxId);
        newStore.setStoreName(storeName);
        newStore.setStorePhone(storePhone);

        return storeRepository.save(newStore);

    }

}
