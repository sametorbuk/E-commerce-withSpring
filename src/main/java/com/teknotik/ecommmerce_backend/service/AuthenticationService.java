package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.Util.JwtUtil;
import com.teknotik.ecommmerce_backend.dto.LoginRequest;
import com.teknotik.ecommmerce_backend.entity.Role;
import com.teknotik.ecommmerce_backend.entity.Store;
import com.teknotik.ecommmerce_backend.entity.User;
import com.teknotik.ecommmerce_backend.exceptions.EcommerceException;
import com.teknotik.ecommmerce_backend.repository.RoleRepository;
import com.teknotik.ecommmerce_backend.repository.StoreRepository;
import com.teknotik.ecommmerce_backend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AuthenticationService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private StoreRepository storeRepository;
    private JwtUtil jwtService;


    @Autowired
    public AuthenticationService(PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                                 UserRepository userRepository, StoreRepository storeRepository,JwtUtil jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.jwtService=jwtService;
    }


    public User customerRegister(String name, String email, String password) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            throw new EcommerceException("This user already exist in system", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(password);

        Optional<Role> findRole = roleRepository.findByAuthority("customer");

        Set<Role> roles = new HashSet<>();
        roles.add(findRole.get());


        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(encodedPassword);
        newUser.setRoles(roles);

        return userRepository.save(newUser);

    }


    public User adminRegister(String name, String email, String password) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            throw new EcommerceException("This user already exist in system", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(password);

        Optional<Role> findRole = roleRepository.findByAuthority("admin");

        Set<Role> roles = new HashSet<>();
        roles.add(findRole.get());


        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(encodedPassword);
        newUser.setRoles(roles);

        return userRepository.save(newUser);

    }


    public Store storeRegister(String name, String email, String password, String storeName, String storePhone, String storeTaxId, String storeBankAccount) {
        Optional<Store> foundUser = storeRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            throw new EcommerceException("This store already exist in system", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(password);


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

    public Map<String,String> login(LoginRequest loginRequest){
        Optional<User> foundUser =userRepository.findByEmail(loginRequest.email());
        Optional<Store> foundStore=storeRepository.findByEmail(loginRequest.email());
        if (foundUser.isPresent()){
            if(authenticateUser(loginRequest.email(), loginRequest.password())){
                String token = jwtService.generateToken(loginRequest.email());
                Map<String ,String> loginResponse=new HashMap<>();
                loginResponse.put("email",foundUser.get().getEmail());
                loginResponse.put("name",foundUser.get().getName());
                loginResponse.put("token" , token);
                return loginResponse;
            }
        } else if (foundStore.isPresent()) {
           if(authenticateUser(loginRequest.email(), loginRequest.password())){
               String token = jwtService.generateToken(loginRequest.email());
               Map<String ,String> loginResponse=new HashMap<>();
               loginResponse.put("token" , token);
               loginResponse.put("email",foundStore.get().getEmail());
               loginResponse.put("name",foundStore.get().getName());
               return loginResponse;
           }
        }

        throw new EcommerceException("There is no account with this email",HttpStatus.NOT_FOUND);
    }



    public boolean authenticateUser(String username , String password){
        Optional<User> foundUser=userRepository.findByEmail(username);
        if(foundUser.isPresent()){
            String encodedPassword=foundUser.get().getPassword();
            if(passwordEncoder.matches(password,encodedPassword)){
               return true;
            }else{
                throw new EcommerceException("Invalid password please try again",HttpStatus.BAD_REQUEST);
            }
        }

        throw new EcommerceException("There is no user with this email",HttpStatus.NOT_FOUND);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByEmail(username);

        if (foundUser.isPresent()) {
            return foundUser.get();
        } else {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
    }
}
