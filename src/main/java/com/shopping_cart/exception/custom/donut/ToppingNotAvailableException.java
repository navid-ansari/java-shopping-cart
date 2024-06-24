package com.shopping_cart.exception.custom.donut;

public class ToppingNotAvailableException extends RuntimeException {
    public ToppingNotAvailableException(String message) {
        super(message);
    }
}
