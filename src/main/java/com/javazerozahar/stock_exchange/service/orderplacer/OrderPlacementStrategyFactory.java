package com.javazerozahar.stock_exchange.service.orderplacer;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderPlacementStrategyFactory {
    private final Map<String, OrderPlacementStrategy> orderPlacementStrategies;

    public OrderPlacementStrategyFactory(
            CreateOrderPlacementStrategy createOrderPlacementStrategy,
            UpdateOrderPlacementStrategy updateOrderPlacementStrategy,
            DeleteOrderPlacementStrategy deleteOrderPlacementStrategy
    ) {
        this.orderPlacementStrategies = new HashMap<>();
        orderPlacementStrategies.put("create", createOrderPlacementStrategy);
        orderPlacementStrategies.put("update", updateOrderPlacementStrategy);
        orderPlacementStrategies.put("delete", deleteOrderPlacementStrategy);
    }

    public OrderPlacementStrategy getOrderPlacementStrategy(String strategyName) {
        return orderPlacementStrategies.get(strategyName);
    }
}
