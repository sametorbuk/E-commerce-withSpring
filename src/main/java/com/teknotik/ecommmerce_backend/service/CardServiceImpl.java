package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.Util.JwtUtil;
import com.teknotik.ecommmerce_backend.dto.CardResponse;
import com.teknotik.ecommmerce_backend.dto.DtoConverter;
import com.teknotik.ecommmerce_backend.entity.CreditCard;
import com.teknotik.ecommmerce_backend.entity.User;
import com.teknotik.ecommmerce_backend.exceptions.EcommerceException;
import com.teknotik.ecommmerce_backend.repository.CrediCardRepository;
import com.teknotik.ecommmerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CardServiceImpl {

    private UserRepository userRepository;
    private CrediCardRepository cardRepository;
    private JwtUtil jwtService;


    @Autowired
    public CardServiceImpl(CrediCardRepository cardRepository, JwtUtil jwtService, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public Set<CardResponse> findAllCard(String token){
        tokenIsValid(token);
        Optional<User> foundUser=userRepository.findByEmail(jwtService.extractUsername(token));
        if(foundUser.isPresent()){
            Set<CardResponse> allCard=new HashSet<>();
            for(CreditCard card : foundUser.get().getCards()){
                CardResponse cardResponse= DtoConverter.cardToCardResponse(card);
                allCard.add(cardResponse);
            }
            return allCard;
        }else{
            throw new EcommerceException("There is no user with this email" , HttpStatus.NOT_FOUND);
        }
    }


    public CardResponse saveCard(String token,CreditCard creditCard){
        tokenIsValid(token);
        CardResponse response = DtoConverter.cardToCardResponse(creditCard);
        Optional<User> foundUser = userRepository.findByEmail(jwtService.extractUsername(token));
        if(foundUser.isPresent()){
            foundUser.get().addCard(creditCard);
            userRepository.save(foundUser.get());
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
                userRepository.save(foundUser.get());
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
                cardRepository.save(creditCard);
                return DtoConverter.cardToCardResponse(creditCard);
            }else{
                throw new EcommerceException("There is no card like this card",HttpStatus.NOT_FOUND);
            }
        }else {
            throw new EcommerceException("There is no user",HttpStatus.NOT_FOUND);
        }
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
