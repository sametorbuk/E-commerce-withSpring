package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findByAuthority(String authority);
}
