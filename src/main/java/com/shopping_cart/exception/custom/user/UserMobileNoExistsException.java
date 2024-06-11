package com.shopping_cart.exception.custom.user;

public class UserMobileNoExistsException extends RuntimeException {
    public UserMobileNoExistsException(String message) {
        super(message);
    }
}
