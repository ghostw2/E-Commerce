package com.example.ecommerce.exceptions;

public class ProductNotExistsException extends IllegalArgumentException{
    public ProductNotExistsException(String msg){
        super(msg);
    }
}
