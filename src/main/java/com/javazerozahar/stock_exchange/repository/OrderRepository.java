package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Order;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(Long orderId);

    void remove(Order order);
}
