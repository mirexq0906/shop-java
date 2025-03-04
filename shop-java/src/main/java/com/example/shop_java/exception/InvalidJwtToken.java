package com.example.shop_java.exception;

public class InvalidJwtToken extends Exception {
    public InvalidJwtToken(String message) {
        super(message);
    }

    public InvalidJwtToken(String message, Throwable cause) {
        super(message, cause);
    }
}
