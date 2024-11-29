package com.javazerozahar.stock_exchange.exceptions;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(Long stockId) {
        super("Stock not found: " + stockId);
    }
}
