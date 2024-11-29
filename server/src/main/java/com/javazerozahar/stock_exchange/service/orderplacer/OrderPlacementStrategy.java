package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.model.entity.Order;

public interface OrderPlacementStrategy {

    /**
     * @return
     * @throws com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException if the criteria aren't met
     */
    Order placeOrder(Order order);
}
