package com.teknotik.ecommmerce_backend.controller;

import com.teknotik.ecommmerce_backend.dto.CardResponse;
import com.teknotik.ecommmerce_backend.entity.CreditCard;
import com.teknotik.ecommmerce_backend.service.CardServiceImpl;
import com.teknotik.ecommmerce_backend.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("user/card")
public class CardController {

    private CardServiceImpl cardService;

    @Autowired
    public CardController(CardServiceImpl cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public Set<CardResponse> findAllCard(@RequestHeader("Authorization") String token) {
        return cardService.findAllCard(token);
    }

    @PostMapping
    public CardResponse saveCard(@RequestHeader("Authorization") String token, @RequestBody CreditCard creditCard) {
        System.out.println("Received CreditCard: " + creditCard);
        return cardService.saveCard(token, creditCard);
    }

    @PutMapping
    public CardResponse updateCard(@RequestHeader("Authorization") String token, @RequestBody CreditCard creditCard) {
        return cardService.updateCard(token, creditCard);
    }

    @DeleteMapping("/{id}")
    public CardResponse deleteCard(@RequestHeader("Authorization") String token, @PathVariable long id) {
        return cardService.deleteCard(token, id);
    }
}
