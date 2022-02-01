package com.example.ecommerce.model;

import com.example.ecommerce.dto.product.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private @NotNull String name;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JsonIgnore
    @JoinColumn(name = "category_id",nullable = false)
    Category category;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product")
    private List<Wishlist> wishListList;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product")
    private List<Cart> carts;

    public Product(ProductDto productDto, Category category) {
        this.name = productDto.getName();
        this.imageUrl = productDto.getImageUrl();
        this.description = productDto.getDescription();
        this.price = productDto.getPrice();
        this.category = category;
    }

    public Product(String name, String imageURL, double price, String description, Category category) {
        super();
        this.name = name;
        this.imageUrl = imageURL;
        this.price = price;
        this.description = description;
        this.category = category;
    }


}
