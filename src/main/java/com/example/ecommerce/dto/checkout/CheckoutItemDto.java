package com.example.ecommerce.dto.checkout;

import lombok.Data;

@Data
public class CheckoutItemDto {
    private String productName;
    private int  quantity;
    private double price;
    private long productId;
    private Long userId;
    public CheckoutItemDto() {}

    public CheckoutItemDto(String productName, int quantity, double price, long productId, Long userId) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
        this.userId = userId;
    }

}
