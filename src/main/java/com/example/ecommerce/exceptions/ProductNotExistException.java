package com.example.ecommerce.exceptions;

public class ProductNotExistException extends IllegalArgumentException{
    public ProductNotExistException(String msg){
        super(msg);
    }
}
