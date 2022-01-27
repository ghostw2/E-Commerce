package com.example.ecommerce.controller;

import com.example.ecommerce.common.ApiResponse;
import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Wishlist;
import com.example.ecommerce.service.TokenService;
import com.example.ecommerce.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private TokenService tokenService;
    //post
    //save product to wishlist
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishlist (@RequestBody Product product,
                                                      @RequestParam("token") String token){
        //authenticate the token
        tokenService.authenticate(token);
        //find the user
        User user = tokenService.getUser(token);
        //save the item in the wishlist
        Wishlist wishlist = new Wishlist(user,product);

        wishlistService.createWishList(wishlist);

        ApiResponse apiResponse = new ApiResponse(true,"Added to wishlist");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }
    //get products from wishlist
    @GetMapping("{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token){
        //authenticate the token
        tokenService.authenticate(token);
        //find the user
        User user = tokenService.getUser(token);
        Long user_id = user.getId();
        List<Wishlist> body = wishlistService.readWishList(user_id);
        //get wishlist
        //List<ProductDto> productDtos = wishlistService.getWishListForUser(user);
//check this!!!!!!!!!!!!!!!!!!!!!
        return  new ResponseEntity<>(productDtos,HttpStatus.OK);
    }
}
