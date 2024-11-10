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


    public AddressResponse saveAddress(String token , Address address){
        tokenIsValid(token);
        Optional<User> foundUser = userRepository.findByEmail(jwtService.extractUsername(token));
        if(foundUser.isPresent()){
            User user = foundUser.get();
            user.addAddress(address);
            userRepository.save(user);
            addressRepository.save(address);
            return DtoConverter.addressToAddressResponse(address);
        } else {
            throw new EcommerceException("There is no user with this token", HttpStatus.NOT_FOUND);
        }
    }


    public AddressResponse deleteAddress(String token , long id) {
        if (id <= 0) {
            throw new EcommerceException("Please enter a valid id", HttpStatus.BAD_REQUEST);
        }
        tokenIsValid(token);
        Optional<User> foundUser = userRepository.findByEmail(jwtService.extractUsername(token));
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            Optional<Address> address = addressRepository.findById(id);
            if (address.isPresent()) {
                user.getAddresses().remove(address.get());
                userRepository.save(user);
                addressRepository.deleteById(id);
                return DtoConverter.addressToAddressResponse(address.get());
            } else {
                throw new EcommerceException("There is no address with this id", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new EcommerceException("There is no user with this token", HttpStatus.NOT_FOUND);
        }
    }

    public AddressResponse updateAddress(String token , Address address){
        tokenIsValid(token);
        Optional<User> foundUser=userRepository.findByEmail(jwtService.extractUsername(token));
        if(foundUser.isPresent()){
           Optional<Address> foundAddress=foundUser.get().getAddresses().stream().filter(addr -> addr.getId() == address.getId()).findFirst();
           if(foundAddress.isPresent()){
               Address existAddress = foundAddress.get();
               existAddress.setCity(address.getCity());
               existAddress.setName(address.getName());
               existAddress.setTitle(address.getTitle());
               existAddress.setPhone(address.getPhone());
               existAddress.setSurname(address.getSurname());
               userRepository.save(foundUser.get());
               addressRepository.save(address);
               return DtoConverter.addressToAddressResponse(address);
           }else{
               throw new EcommerceException("There is no address with this id",HttpStatus.NOT_FOUND);
           }
        }else{
            throw new EcommerceException("There is no user with this token",HttpStatus.NOT_FOUND);
        }
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
