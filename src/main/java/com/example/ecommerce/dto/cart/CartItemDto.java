package com.example.ecommerce.dto.cart;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private Product product;

    public CartItemDto() {
    }

    public CartItemDto(Cart cart) {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }
}
