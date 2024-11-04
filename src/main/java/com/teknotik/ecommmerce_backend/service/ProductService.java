package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.entity.Product;
import com.teknotik.ecommmerce_backend.repository.ProductRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(String filter , String sort , int limit , int offset);
}
