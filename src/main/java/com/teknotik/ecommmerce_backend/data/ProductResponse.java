package com.teknotik.ecommmerce_backend.data;

import com.teknotik.ecommmerce_backend.entity.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Data
@Component
@NoArgsConstructor
public class ProductResponse {
    private List<Product> products;

}