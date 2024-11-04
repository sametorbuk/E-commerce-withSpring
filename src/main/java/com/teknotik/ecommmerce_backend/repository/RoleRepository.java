package com.teknotik.ecommmerce_backend.repository;

import com.teknotik.ecommmerce_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
