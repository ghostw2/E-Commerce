package com.example.ecommerce.dto.order;

import com.example.ecommerce.model.Order;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class OrderDto {
    private Long id;
    private @NotNull Integer userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.setId(order.getId());
        //this.setUserId(order.getUserId());
    }
}
