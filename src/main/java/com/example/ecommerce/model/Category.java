package com.example.ecommerce.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Integer Id;

    @Column(name = "category_name")
    private @NotBlank String categoryName;

    private @NotBlank String description ;

    private @NotBlank String imageUrl;

}
