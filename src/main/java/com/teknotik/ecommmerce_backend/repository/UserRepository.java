package com.teknotik.ecommmerce_backend.repository;

import com.teknotik.ecommmerce_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
