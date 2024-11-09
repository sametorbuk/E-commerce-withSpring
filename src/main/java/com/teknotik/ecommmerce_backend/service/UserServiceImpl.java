package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.util.JwtUtil;
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


    public Set<AddressResponse> findAllAddress(String token){
        tokenIsValid(token);
        String email = jwtService.extractUsername(token);
        Optional<User> foundUser=userRepository.findByEmail(email);
        if(foundUser.isPresent()){
            Set<Address> addresses = foundUser.get().getAddresses();
            Set<AddressResponse> response = new HashSet<>();
            for(Address add : addresses){
                response.add(new AddressResponse(add.getTitle(),add.getName(),add.getSurname(),add.getPhone() , add.getCity()));
            }
            return response;
        }
        throw new RuntimeException("Error");
    }


    public Set<CardResponse> findAllCard(String token){
      tokenIsValid(token);
        Optional<User> foundUser=userRepository.findByEmail(jwtService.extractUsername(token));
        if(foundUser.isPresent()){
            Set<CardResponse> allCard=new HashSet<>();
            for(CreditCard card : foundUser.get().getCards()){
                CardResponse cardResponse= DtoConverter.cardToCardResponse(card);
                allCard.add(cardResponse);
                return allCard;
            }
        }else{
            throw new EcommerceException("There is no user with this email" , HttpStatus.NOT_FOUND);
        }
       throw new EcommerceException();
    }


    public CardResponse saveCard(String token,CreditCard creditCard){
       tokenIsValid(token);
        CardResponse response = DtoConverter.cardToCardResponse(creditCard);
       Optional<User> foundUser = userRepository.findByEmail(jwtService.extractUsername(token));
       if(foundUser.isPresent()){
           foundUser.get().addCart(creditCard);
           cardRepository.save(creditCard);
           return DtoConverter.cardToCardResponse(creditCard);
       }else{
           throw new EcommerceException("There is no user with this email",HttpStatus.NOT_FOUND);
       }
    }


    public CardResponse deleteCard(String token , long cardId){
        tokenIsValid(token);
        if(cardId <= 0){
            throw new EcommerceException("Please enter a valid card_id",HttpStatus.BAD_REQUEST);
        }
        Optional<CreditCard> foundCard = cardRepository.findById(cardId);
        if(foundCard.isPresent()){
            Optional<User> foundUser = userRepository.findByEmail(jwtService.extractUsername(token));
            if (foundUser.isPresent()){
                foundUser.get().getCards().remove(foundCard.get());
                cardRepository.delete(foundCard.get());
                return DtoConverter.cardToCardResponse(foundCard.get());
            }else{
                throw new EcommerceException("There is no user with this email",HttpStatus.NOT_FOUND);
            }

        }else {
            throw new EcommerceException("There is no card with this id",HttpStatus.NOT_FOUND);
        }
    }


    public CardResponse updateCard(String token , CreditCard creditCard){
        tokenIsValid(token);
        Optional<User> foundUser = userRepository.findByEmail(jwtService.extractUsername(token));
        if (foundUser.isPresent()){
            Optional<CreditCard> existCardFound = foundUser.get().getCards().stream().filter(card -> card.getId() == creditCard.getId()).findFirst();
            if(existCardFound.isPresent()){
                CreditCard existCard = existCardFound.get();
                existCard.setCardNo(creditCard.getCardNo());
                existCard.setNameOnCard(creditCard.getNameOnCard());
                existCard.setCvv(creditCard.getCvv());
                existCard.setExpireMonth(creditCard.getExpireMonth());
                existCard.setExpireYear(creditCard.getExpireYear());
               userRepository.save(foundUser.get());
               return DtoConverter.cardToCardResponse(creditCard);
            }
        }else {
            throw new EcommerceException("There is no user",HttpStatus.NOT_FOUND);
        }
        throw  new EcommerceException();
    }




    public void tokenIsValid(String token){
        if(jwtService.isTokenExpired(token)){
            throw new EcommerceException("This token has expired",HttpStatus.BAD_REQUEST);
        }
        if(jwtService.extractUsername(token) == null){
            throw new EcommerceException("There is no user with this token",HttpStatus.NOT_FOUND);
        }
    }



}
