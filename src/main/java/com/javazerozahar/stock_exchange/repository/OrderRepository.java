package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(Long orderId);

    void remove(Order order);

    List<Order> findByStock(Stock stock);
}
