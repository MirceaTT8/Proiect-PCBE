package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.service.orderplacer.OrderPlacer;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

public class OrderService {

    private final OrderPlacer orderPlacer;

    public OrderService() {
        this.orderPlacer = SingletonFactory.getInstance(OrderPlacer.class);
    }

    public void placeOrder(OrderDTO orderDTO, String orderStrategy) {
        orderPlacer.placeOrder(orderDTO, orderStrategy);
    }
}
