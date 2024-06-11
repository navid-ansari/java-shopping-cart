package com.shopping_cart.exception.custom.user;

public class UserEmailExistsException extends RuntimeException {
    public UserEmailExistsException(String message) {
        super(message);
    }
}
