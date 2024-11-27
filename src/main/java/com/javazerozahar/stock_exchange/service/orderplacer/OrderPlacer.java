package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.rabbit.order.OrderPlacerProducer;
import com.javazerozahar.stock_exchange.service.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class OrderPlacer {

    private final OrderPlacerProducer orderPlacerProducer;
    private final OrderPlacementStrategyFactory orderPlacementStrategyFactory;

    /**
     * @throws com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException if the criteria aren't met
     */
    public Order placeOrder(Order order, String orderPlacementStrategy) {
        order = orderPlacementStrategyFactory.getOrderPlacementStrategy(orderPlacementStrategy).placeOrder(order);

        orderPlacerProducer.sendOrder(order);

        return order;
    }
}
