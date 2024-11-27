package com.teknotik.ecommmerce_backend.repository;

import com.teknotik.ecommmerce_backend.entity.RefreshToken;
import com.teknotik.ecommmerce_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken , Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

    Optional<RefreshToken> findByUser(User user);

}
