package com.example.ecommerce.dto.order;

import com.example.ecommerce.model.Order;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class OrderDto {
    private Long id;
    private @NotNull Integer userId;

    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.setId(order.getId());
        //this.setUserId(order.getUserId());
    }
}
