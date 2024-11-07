package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.data.ProductResponse;
import com.teknotik.ecommmerce_backend.entity.Category;
import com.teknotik.ecommmerce_backend.entity.Product;
import com.teknotik.ecommmerce_backend.repository.CategoryRepository;
import com.teknotik.ecommmerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository ) {
        this.productRepository = productRepository;

    }

    public void saveProductsFromApi() {
        String url = "https://workintech-fe-ecommerce.onrender.com/products?limit=400";
        RestTemplate restTemplate = new RestTemplate();


        ProductResponse response = restTemplate.getForObject(url, ProductResponse.class);

        if (response != null && response.getProducts() != null) {
            for (Product product : response.getProducts()) {

                productRepository.save(product);
            }
        }
    }


    @Override
    public List<Product> getProducts(String filter , String sort , int limit , int offset) {
        return productRepository.getProducts(filter, sort , limit,offset);
    }
}
