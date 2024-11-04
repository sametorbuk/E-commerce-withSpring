package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.data.ProductResponse;
import com.teknotik.ecommmerce_backend.entity.Category;
import com.teknotik.ecommmerce_backend.entity.Product;
import com.teknotik.ecommmerce_backend.repository.CategoryRepository;
import com.teknotik.ecommmerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository ) {
        this.productRepository = productRepository;

    }


    @Override
    public List<Product> getProducts(String filter , String sort , int limit , int offset) {
        return productRepository.getProducts(filter, sort , limit,offset);
    }
}
