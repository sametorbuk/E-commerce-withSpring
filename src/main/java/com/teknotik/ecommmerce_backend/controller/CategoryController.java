package com.teknotik.ecommmerce_backend.controller;

import com.teknotik.ecommmerce_backend.entity.Category;
import com.teknotik.ecommmerce_backend.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryServiceImpl categoryService;


    @Autowired
    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public List<Category> findAll(){
      return categoryService.findAll();
    }
}
