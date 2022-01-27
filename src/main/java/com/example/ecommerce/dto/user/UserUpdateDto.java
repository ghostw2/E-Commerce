package com.example.ecommerce.dto.user;

import lombok.Data;

@Data
public class UserUpdateDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Role role;
}
