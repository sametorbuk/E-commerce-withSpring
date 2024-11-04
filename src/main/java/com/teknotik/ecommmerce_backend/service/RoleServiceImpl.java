package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.data.ProductResponse;
import com.teknotik.ecommmerce_backend.data.RolesResponse;
import com.teknotik.ecommmerce_backend.entity.Product;
import com.teknotik.ecommmerce_backend.entity.Role;
import com.teknotik.ecommmerce_backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void fetchAllRoles(){
        RestTemplate restTemplate =new RestTemplate();
        String url="https://workintech-fe-ecommerce.onrender.com/roles";

        Role[] rolesArray = restTemplate.getForObject(url, Role[].class);
        Set<Role> roles = new HashSet<>(Arrays.asList(rolesArray));

        roleRepository.saveAll(roles);

       Role rl = new Role(3,"customer","Müşteri");
       roleRepository.save(rl);
    }


}
