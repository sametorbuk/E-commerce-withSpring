package com.teknotik.ecommmerce_backend;

import com.teknotik.ecommmerce_backend.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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
