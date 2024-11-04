package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.entity.Store;

import java.util.Optional;

public interface StoreService {
    Store findByEmail(String email);
}
