package com.javazerozahar.stock_exchange.repository.repositoryImpl;

import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.OrderRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {

    private final Map<Long, Order> orderStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Order save(Order order) {
        if (order.getOrderId() == null) {
            order.setOrderId(idCounter.incrementAndGet());
        }
        orderStore.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return Optional.ofNullable(orderStore.get(orderId));
    }

    @Override
    public void remove(Order order) {
        if (order.getOrderId() != null) {
            orderStore.remove(order.getOrderId());
        }
    }

    @Override
    public List<Order> findByBoughtStock(Stock stock) {
        return orderStore.values().stream()
                .filter(order -> order.getBoughtStock().equals(stock))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findBySoldStock(Stock stock) {
        return orderStore.values().stream()
                .filter(order -> order.getSoldStock().equals(stock))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findAll() {
        return orderStore.values().stream().toList();
    }
}

