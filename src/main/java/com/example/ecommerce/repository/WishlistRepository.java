package com.example.ecommerce.repository;

import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    public List<Wishlist>findAllByUserOrderByCreatedDateDesc(User user);
    List<Wishlist> findAllByUserIdOrderByCreatedDateDesc(Integer userId);
}
