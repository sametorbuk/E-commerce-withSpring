package com.teknotik.ecommmerce_backend.repository;

import com.teknotik.ecommmerce_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "SELECT * FROM products " +
            "WHERE description ILIKE CONCAT('%', :filter, '%') " +
            "ORDER BY price " +
            ":sort " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Product> getProducts(@Param("filter") String filter,
                              @Param("sort") String sort,
                              @Param("limit") int limit,
                              @Param("offset") int offset);


}
