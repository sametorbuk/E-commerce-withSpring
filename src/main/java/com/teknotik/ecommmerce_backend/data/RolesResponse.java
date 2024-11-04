package com.teknotik.ecommmerce_backend.data;

import com.teknotik.ecommmerce_backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolesResponse {

    private Set<Role> roles;
}
