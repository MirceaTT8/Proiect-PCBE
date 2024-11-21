package com.javazerozahar.stock_exchange.utils;

import org.springframework.stereotype.Service;

@Service
public class CurrencyConverter {
    public double convert(double fromPrice, double toPrice, double fromQuantity) {
        return fromQuantity * fromPrice / toPrice;
    }
}
