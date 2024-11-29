package com.javazerozahar.stock_exchange.utils;

public interface Lockable<T> {
    void lockOrder(T criterion);
    void unlockOrder(T criterion);
}
