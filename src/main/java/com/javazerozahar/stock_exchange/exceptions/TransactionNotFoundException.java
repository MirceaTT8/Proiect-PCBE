package com.javazerozahar.stock_exchange.exceptions;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long transactionId) {
        super("Transaction with id " + transactionId + " not found");
    }
}
