package com.teknotik.ecommmerce_backend.repository;

import com.teknotik.ecommmerce_backend.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

    @Query("SELECT s FROM Store s WHERE s.email = :email")
    Optional<Store> findByEmail(String email);
}
