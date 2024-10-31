package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.service.orderplacer.OrderPlacer;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderService {

    private final OrderPlacer orderPlacer;
    private final OrderMatcher orderMatcher;


    private final ConcurrentHashMap<Long, Lock> stockLocks = new ConcurrentHashMap<>();

    public OrderService() {
        this.orderPlacer = SingletonFactory.getInstance(OrderPlacer.class);
        this.orderMatcher = SingletonFactory.getInstance(OrderMatcher.class);
    }

    public void placeOrder(OrderDTO orderDTO, String orderStrategy) {

        Lock lock = stockLocks.computeIfAbsent(orderDTO.getBoughtStockId(), _ -> new ReentrantLock(true));

        Order order = orderPlacer.placeOrder(orderDTO, orderStrategy);

//        lock.lock();
        try {
            orderMatcher.matchOrder(order);
        } finally {
//            lock.unlock();
        }

    }

}
