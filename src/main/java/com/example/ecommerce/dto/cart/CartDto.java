package com.example.ecommerce.dto.cart;

import lombok.Data;

import java.util.List;
@Data
public class CartDto {
    private List<CartItemDto> cartItems;
    private double totalCost;
    public CartDto() {
    }
    public CartDto(List<CartItemDto> cartItemDtoList, double totalCost) {
        this.cartItems = cartItemDtoList;
        this.totalCost = totalCost;
    }
}
