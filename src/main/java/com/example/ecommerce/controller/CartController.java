package com.example.ecommerce.controller;

import com.example.ecommerce.common.ApiResponse;
import com.example.ecommerce.dto.cart.AddToCartDto;
import com.example.ecommerce.dto.cart.CartDto;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token)throws AuthenticationFailException, ProductNotExistException  {
        // authenticate the token
        tokenService.authenticate(token);
        // find the user
        User user = tokenService.getUser(token);
        Product product = productService.findById(addToCartDto.getProductId());
        System.out.println("product to add"+  product.getName());
        cartService.addToCart(addToCartDto, user );

        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }
    // get all cart items for a user
    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token)
            throws AuthenticationFailException{
        // authenticate the token
        tokenService.authenticate(token);
        // find the user
        User user = tokenService.getUser(token);
        // get cart items
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
    // delete a cart item for a user

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Long itemId,
                                                      @RequestParam("token") String token)
    throws AuthenticationFailException, CartItemNotExistException{
        // authenticate the token
        tokenService.authenticate(token);
        // find the user
        Long userId = tokenService.getUser(token).getId();
        cartService.deleteCartItem(itemId, userId);
        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);

    }
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCart(@RequestBody @Valid AddToCartDto addToCartDto,
                                                    @RequestParam("token") String token)
            throws AuthenticationFailException,ProductNotExistException{
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        Product product = productService.findById(addToCartDto.getProductId());
        cartService.updateCartItem(addToCartDto,user,product);
        return  new ResponseEntity<>(new ApiResponse(true,"Product has been updated"),HttpStatus.OK);
    }



}
