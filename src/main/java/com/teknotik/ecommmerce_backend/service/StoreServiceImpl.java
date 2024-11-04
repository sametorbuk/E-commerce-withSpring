package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.entity.Store;
import com.teknotik.ecommmerce_backend.exceptions.EcommerceException;
import com.teknotik.ecommmerce_backend.repository.StoreRepository;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class StoreServiceImpl implements StoreService{


    private StoreRepository storeRepository;

    @Override
    public Store findByEmail(String email) {
        Optional<Store> foundStore = storeRepository.findByEmail(email);
        if(foundStore.isPresent()){
            return foundStore.get();
        }
        throw new EcommerceException("There is no store with this email" + email , HttpStatus.NOT_FOUND);
    }
}
