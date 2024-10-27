package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;

public interface OrderPlacementStrategy {

    /**
     * @throws com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException if the criteria aren't met
     */
    void placeOrder(Portfolio portfolio, Stock stock, OrderDTO orderDTO);
}
