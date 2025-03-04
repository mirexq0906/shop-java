package com.example.shop_java.exception;

public class OrderCreateException extends RuntimeException {
    
    public OrderCreateException(String message) {
        super(message);
    }

    public OrderCreateException(String message, Throwable cause) {
        super(message, cause);
    }

}
