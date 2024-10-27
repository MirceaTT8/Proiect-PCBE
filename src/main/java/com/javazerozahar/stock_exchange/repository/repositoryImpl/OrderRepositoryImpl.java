package com.javazerozahar.stock_exchange.repository.repositoryImpl;

import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.repository.OrderRepository;

import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return Optional.empty();
    }

    @Override
    public void remove(Order order) {

    }
}
