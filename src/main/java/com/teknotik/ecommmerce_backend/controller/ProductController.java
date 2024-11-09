package com.teknotik.ecommmerce_backend.controller;


import com.teknotik.ecommmerce_backend.entity.Product;
import com.teknotik.ecommmerce_backend.service.ProductService;
import com.teknotik.ecommmerce_backend.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<Product> getProducts(@RequestParam(value = "filter",required = false,defaultValue = "") String filter
            ,@RequestParam(value = "sort",required = false,defaultValue = "asc") String sort
            ,@RequestParam(value = "limit",required = false ,defaultValue = "25")int limit
            ,@RequestParam(value = "offset",required = false ,defaultValue = "0")int offset){
        return productService.getProducts(filter,sort,limit,offset);
    }
}
