package com.example.ecommerce.exceptions;

public class CartItemNotExistException extends  IllegalArgumentException{
    public CartItemNotExistException(String msg) {
        super(msg);
    }
}
