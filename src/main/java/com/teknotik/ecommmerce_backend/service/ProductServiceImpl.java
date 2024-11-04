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
    private CategoryRepository categoryRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository , CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository=categoryRepository;
    }

    public void saveProducts(){
        RestTemplate restTemplate =new RestTemplate();
        String url="https://workintech-fe-ecommerce.onrender.com/products?page=%d&limit=300";

        ProductResponse productResponse = restTemplate.getForObject(url, ProductResponse.class);
        List<Product> products = productResponse.getProducts();

        System.out.println("Gelen Ürünler: " + products);


        for(Product pr : products){
            Optional<Category> foundCat = categoryRepository.findById(pr.getCatnum());
            String imgUrl = pr.getImages().get(0).getUrl();
            pr.setImgUrl(imgUrl);
            if(foundCat.isPresent()){
                pr.setCategory(foundCat.get());
            }
        }


        if (products != null) {
            productRepository.saveAll(products);
        }

    }


    @Override
    public List<Product> getProducts(String filter , String sort , int limit , int offset) {
        return productRepository.getProducts(filter, sort , limit,offset);
    }
}
