package com.teknotik.ecommmerce_backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "card" , schema = "ecommerce")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "expire_month")
    private String expireMonth;

    @Column(name = "expire_year")
    private String expireYear;

    @Column(name = "card_no")
    private String cardNo;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "name_on_card")
    private String nameOnCard;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;


    public CreditCard(String expireMonth , String expireYear,String cardNo, String nameOnCard) {
        this.cardNo = cardNo;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.nameOnCard = nameOnCard;

    }
}
