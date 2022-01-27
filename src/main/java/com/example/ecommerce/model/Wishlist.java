package com.example.ecommerce.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private User user;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Wishlist() {
    }

    public Wishlist(User user,Product product){
        this.user = user;
        this.product = product;
    }
    public Wishlist(User user, Product product) {
        this.user = user;
        this.product = product;
        this.createdDate = new Date();
    }

}
