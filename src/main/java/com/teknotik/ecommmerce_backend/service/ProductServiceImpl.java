package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.data.ProductResponse;
import com.teknotik.ecommmerce_backend.entity.Product;
import com.teknotik.ecommmerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    public void saveProducts(){
        RestTemplate restTemplate =new RestTemplate();
        String url="https://workintech-fe-ecommerce.onrender.com/products";

        ProductResponse productResponse = restTemplate.getForObject(url, ProductResponse.class);
        List<Product> products = productResponse.getProducts();

        if (products != null) {
            productRepository.saveAll(products);
        }

    }
}
