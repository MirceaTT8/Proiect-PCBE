package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.service.orderplacer.OrderPlacer;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

public class OrderService {

    private final OrderPlacer orderPlacer;
    private final OrderMatcher orderMatcher;

    public OrderService() {
        this.orderPlacer = SingletonFactory.getInstance(OrderPlacer.class);
        this.orderMatcher = SingletonFactory.getInstance(OrderMatcher.class);
    }

    public synchronized void placeOrder(OrderDTO orderDTO, String orderStrategy) {

        Order order = orderPlacer.placeOrder(orderDTO, orderStrategy);
        orderMatcher.matchOrder(order);

    }

}
