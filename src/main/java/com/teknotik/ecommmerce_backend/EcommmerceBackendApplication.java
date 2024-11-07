package com.teknotik.ecommmerce_backend;

import com.teknotik.ecommmerce_backend.entity.Product;
import com.teknotik.ecommmerce_backend.entity.User;
import com.teknotik.ecommmerce_backend.repository.ProductRepository;
import com.teknotik.ecommmerce_backend.service.CategoryServiceImpl;
import com.teknotik.ecommmerce_backend.service.ProductService;
import com.teknotik.ecommmerce_backend.service.ProductServiceImpl;
import com.teknotik.ecommmerce_backend.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EcommmerceBackendApplication implements CommandLineRunner {

    @Autowired
	private ProductServiceImpl productService;


	public static void main(String[] args) {
		SpringApplication.run(EcommmerceBackendApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

	}



}
