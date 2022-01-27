package com.example.ecommerce.dto.user;

import com.example.ecommerce.enums.Role;
import lombok.Data;

@Data
public class UserCreateDto {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String password;
}
