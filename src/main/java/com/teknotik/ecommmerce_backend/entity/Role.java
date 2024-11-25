package com.teknotik.ecommmerce_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "role", schema = "ecommerce")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "authority")
    @JsonProperty(namespace = "code")
    private String authority;



    @ManyToMany(mappedBy = "roles")
    List<User> users;

    public Role(long id, String authority, String name) {
        this.id = id;
        this.authority = authority;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
