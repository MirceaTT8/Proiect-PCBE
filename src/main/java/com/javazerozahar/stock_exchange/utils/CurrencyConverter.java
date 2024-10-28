package com.javazerozahar.stock_exchange.utils;

public class CurrencyConverter {
    public double convert(double fromPrice, double toPrice, double fromQuantity) {
        return fromQuantity * fromPrice / toPrice;
    }
}
