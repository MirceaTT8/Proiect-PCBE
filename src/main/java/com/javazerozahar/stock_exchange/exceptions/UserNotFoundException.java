package com.javazerozahar.stock_exchange.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("User with id " + userId + " not found");
    }
}
