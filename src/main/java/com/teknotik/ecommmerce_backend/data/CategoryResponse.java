package com.teknotik.ecommmerce_backend.data;

import com.teknotik.ecommmerce_backend.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class CategoryResponse {

    private List<Category> categories;
}
