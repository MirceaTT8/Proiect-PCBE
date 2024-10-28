package com.javazerozahar.stock_exchange.utils;

import java.util.concurrent.ConcurrentHashMap;

public class SingletonFactory {
    private static final ConcurrentHashMap<Class<?>, Object> instances = new ConcurrentHashMap<>();

    public static <T> T getInstance(Class<T> clazz) {
        T instance = (T) instances.get(clazz);
        if (instance == null) {
            synchronized (instances) {
                instance = (T) instances.get(clazz);
                if (instance == null) {
                    instance = createInstance(clazz);
                    instances.put(clazz, instance);
                }
            }
        }
        return instance;
    }

    private static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create an instance of " + clazz.getName(), e);
        }
    }
}