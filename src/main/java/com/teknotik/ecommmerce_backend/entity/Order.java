package com.teknotik.ecommmerce_backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order", schema = "ecommerce")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "address_id")
    @JsonProperty("address_id")
    private long addressId;

    @Column(name = "order_date")
    @JsonProperty("order_date")
    private String orderDate;

    @Column(name = "card_no")
    @JsonProperty("card_no")
    private String cardNo;

    @Column(name = "card_name")
    @JsonProperty("card_name")
    private String cardName;

    @Column(name = "card_expire_month")
    @JsonProperty("card_expire_month")
    private String cardExpireMonth;

    @Column(name = "card_expire_year")
    @JsonProperty("card_expire_year")
    private String cardExpireYear;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "price")
    private double price;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;


    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    @JoinTable(name = "order_order_product" , joinColumns =@JoinColumn(name="order_id") , inverseJoinColumns = @JoinColumn(name = "order_product_id"))
    List<OrderProduct> orderProducts;


}
