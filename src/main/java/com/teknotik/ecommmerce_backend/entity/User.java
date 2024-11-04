package com.teknotik.ecommmerce_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user", schema = "ecommerce")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",schema = "ecommerce",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles;

    @OneToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH} , mappedBy = "user")
    Set<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "user")
    Set<CreditCard> cards;


    public User(String name, String email, String password, int roleId) {
        this.name = name;
        this.email = email;
        this.password = password;

        switch (roleId){
            case 1:
                Role role1 = new Role(1 ,"ADMIN","ADMIN");
                roles.add(role1);
                break;
            case 2:
                Role role2 = new Role(2 ,"STORE","VENDOR");
                roles.add(role2);
                break;
            case 3:
                Role role3 = new Role(3 ,"USER","CUSTOMER");
                roles.add(role3);
                break;
        }
    }
}
