package com.teknotik.ecommmerce_backend.dto;

import com.teknotik.ecommmerce_backend.entity.Address;
import com.teknotik.ecommmerce_backend.entity.CreditCard;
import com.teknotik.ecommmerce_backend.entity.Product;
import com.teknotik.ecommmerce_backend.entity.User;

public class DtoConverter {


    public static AddressResponse addressToAddressResponse(Address address){
        return new AddressResponse(address.getTitle(), address.getName(), address.getSurname(), address.getPhone(), address.getCity());
    }

    public static CardResponse cardToCardResponse(CreditCard creditCard){
        return new CardResponse(creditCard.getExpireMonth(), creditCard.getExpireYear(), creditCard.getCardNo(), creditCard.getNameOnCard());
    }

    public static UserResponse userToUserResponse(User user){
        return new UserResponse(user.getName(), user.getEmail(), user.getId());
    }


}
