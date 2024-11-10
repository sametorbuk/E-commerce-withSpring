package com.teknotik.ecommmerce_backend.service;
import com.teknotik.ecommmerce_backend.Util.JwtUtil;
import com.teknotik.ecommmerce_backend.dto.AddressResponse;
import com.teknotik.ecommmerce_backend.dto.CardResponse;
import com.teknotik.ecommmerce_backend.dto.DtoConverter;
import com.teknotik.ecommmerce_backend.entity.Address;
import com.teknotik.ecommmerce_backend.entity.CreditCard;
import com.teknotik.ecommmerce_backend.entity.User;
import com.teknotik.ecommmerce_backend.exceptions.EcommerceException;
import com.teknotik.ecommmerce_backend.repository.AddressRepository;
import com.teknotik.ecommmerce_backend.repository.CrediCardRepository;
import com.teknotik.ecommmerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private CrediCardRepository cardRepository;
    private AddressRepository addressRepository;
    private JwtUtil jwtService;

    @Autowired
    public UserServiceImpl(JwtUtil jwtService, UserRepository userRepository, CrediCardRepository cardRepository, AddressRepository addressRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.addressRepository = addressRepository;
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
