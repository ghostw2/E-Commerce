package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Wishlist;
import com.example.ecommerce.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepo;
    @Autowired
    ProductService productService;

    public void createWishList(Wishlist wishlist){

        wishlistRepo.save(wishlist);
    }

//    public List<ProductDto> getWishListForUser(User user){
//        final List<Wishlist> wishlists = wishlistRepo.findAllByUserOrderByCreatedDateDesc(user);
//        List<ProductDto> productDtos = new ArrayList<>();
//        for(Wishlist wishlist : wishlists){
//            productDtos.add(productService.getProductDto(wishlist.getProduct()));
//        }
//        return productDtos;
//    }
    public List<Wishlist> readWishList(Integer userId) {
        return wishlistRepo.findAllByUserIdOrderByCreatedDateDesc(userId);
    }
}
