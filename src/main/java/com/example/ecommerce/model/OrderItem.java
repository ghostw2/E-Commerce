package com.example.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "orderitems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "quantity")
    private @NotNull int quantity;

    @Column(name = "price")
    private @NotNull double price;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private  Product product;
    public OrderItem(){}

    public OrderItem(Order order, @NotNull Product product, @NotNull int quantity, @NotNull double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.order= order;
        this.createdDate = new Date();
    }

}
