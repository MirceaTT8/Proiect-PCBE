package com.javazerozahar.stock_exchange.utils;

import java.util.concurrent.ConcurrentHashMap;

public class SingletonFactory {
    private static final ConcurrentHashMap<Class<?>, Object> instances = new ConcurrentHashMap<>();

    public static synchronized <T> T getInstance(Class<T> clazz) {
        return (T) instances.computeIfAbsent(clazz, SingletonFactory::createInstance);
    }

    private static synchronized <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create an instance of " + clazz.getName(), e);
        }
    }
}