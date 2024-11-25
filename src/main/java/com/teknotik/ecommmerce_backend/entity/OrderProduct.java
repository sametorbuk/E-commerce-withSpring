package com.teknotik.ecommmerce_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_product" , schema = "ecommerce")
@Getter
@Setter
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_id")
    @JsonProperty("product_id")
    private long productId;

    @Column(name = "count")
    private int count;

    @Column(name = "detail")
    private String detail;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH} , mappedBy = "orderProducts",fetch = FetchType.LAZY)
    Set<Order> orders;
}
