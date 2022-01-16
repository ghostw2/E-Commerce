package com.example.ecommerce.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDto {
    //for create optional
    //for update we need it

    private Long id ;

    private @NotNull String name;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull String imageUrl;
    private @NotNull Integer categoryId;

    public ProductDto(){}
}
