package com.teknotik.ecommmerce_backend.repository;

import com.teknotik.ecommmerce_backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
