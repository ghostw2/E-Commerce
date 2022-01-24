package com.example.ecommerce.repository;

import com.example.ecommerce.model.AuthenticationToken;
import com.example.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);
}
