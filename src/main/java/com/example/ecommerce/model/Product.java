package com.example.ecommerce.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
}
