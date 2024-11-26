package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.data.CategoryResponse;
import com.teknotik.ecommmerce_backend.data.ProductResponse;
import com.teknotik.ecommmerce_backend.entity.Category;
import com.teknotik.ecommmerce_backend.entity.Product;
import com.teknotik.ecommmerce_backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class CategoryServiceImpl {

    private final CategoryRepository categoryRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, RestTemplate restTemplate) {
        this.categoryRepository = categoryRepository;
        this.restTemplate = restTemplate;
    }

    public void saveAllCategories() {
        String url = "https://workintech-fe-ecommerce.onrender.com/categories";

        List<Category> categories = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>() {}
        ).getBody();

        if (categories != null) {
            categoryRepository.saveAll(categories);
        }
    }


    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
}