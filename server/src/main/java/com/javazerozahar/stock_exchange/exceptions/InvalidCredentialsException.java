package com.javazerozahar.stock_exchange.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid login credentials");
    }
}
