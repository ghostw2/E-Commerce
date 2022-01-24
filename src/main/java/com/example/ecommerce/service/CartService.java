package com.example.ecommerce.service;

import com.example.ecommerce.dto.cart.AddToCartDto;
import com.example.ecommerce.dto.cart.CartDto;
import com.example.ecommerce.dto.cart.CartItemDto;
import com.example.ecommerce.exceptions.CartItemNotExistException;
import com.example.ecommerce.exceptions.CustomException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    CartRepository cartRepository;
    public CartService(){}

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(AddToCartDto addToCartDto,Product product, User user) {
            Cart cart = new Cart(product,addToCartDto.getQuantity(),user);
            cartRepository.save(cart);

    }
    public CartDto listCartItems(User user) {
        //get user items in cart
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        //initate items in cart iside a list
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart: cartList) {
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        //instantiate cart with items inside price
        for(CartItemDto  cartItemDto : cartItems ){
            totalCost += (cartItemDto.getQuantity() * cartItemDto.getProduct().getPrice());
        }
        return  new CartDto(cartItems,totalCost);
    }
        public static CartItemDto getDtoFromCart(Cart cart){
            return new CartItemDto(cart);
        }

    public void deleteCartItem(Long cartItemId, Long  userId) {
        // the item id belongs to user
        if (!cartRepository.existsById(cartItemId)) {
            throw new CartItemNotExistException("Cart id is invalid : " + cartItemId);
        }
        cartRepository.deleteById(cartItemId);
    }
    public void updateCartItem(AddToCartDto addToCartDto, User user,Product product) {
        Cart cart  = cartRepository.getById(addToCartDto.getId());
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);

    }
    public void deleteCartItems(int userId) {
        cartRepository.deleteAll();
    }
    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }


}
