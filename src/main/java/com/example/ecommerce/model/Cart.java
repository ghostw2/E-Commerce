package com.example.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Enabled;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class , fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private User user;

    private int quantity;

    public Cart(){

    }
    public Cart(Product product, int quantity, User user){
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.createdDate = new Date();
    }
}
