package com.example.ecommerce.model;

import com.example.ecommerce.dto.product.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity

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


    public Product() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Wishlist> getWishListList() {
        return wishListList;
    }

    public void setWishListList(List<Wishlist> wishListList) {
        this.wishListList = wishListList;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
