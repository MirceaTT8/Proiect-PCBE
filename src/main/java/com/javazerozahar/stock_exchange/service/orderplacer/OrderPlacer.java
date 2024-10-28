package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.dto.OrderDtoWithId;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.service.StockService;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderPlacer {

    private final StockService stockService;

    private final Map<String, OrderPlacementStrategy> orderPlacementStrategies;

    private final ConcurrentHashMap<Long, Lock> userLocks = new ConcurrentHashMap<>();

    public OrderPlacer() {
        this.stockService = SingletonFactory.getInstance(StockService.class);

        this.orderPlacementStrategies = new HashMap<>();
        orderPlacementStrategies.put("create", SingletonFactory.getInstance(CreateOrderPlacementStrategy.class));
        orderPlacementStrategies.put("update", SingletonFactory.getInstance(UpdateOrderPlacementStrategy.class));
        orderPlacementStrategies.put("delete", SingletonFactory.getInstance(DeleteOrderPlacementStrategy.class));
    }

    /**
     * @throws com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException if the criteria aren't met
     */
    public Order placeOrder(OrderDTO orderDTO, String orderPlacementStrategy) {

        Stock soldStock = stockService.getStock(orderDTO.getSoldStockId());
        Stock boughtStock = stockService.getStock(orderDTO.getBoughtStockId());

        return placeOrder(getOrder(orderDTO, boughtStock, soldStock), orderPlacementStrategy);
    }

    public Order placeOrder(Order order, String orderPlacementStrategy) {

        Lock lock = userLocks.computeIfAbsent(order.getUserId(), _ -> new ReentrantLock(true));

        lock.lock();
        try {
            return orderPlacementStrategies.get(orderPlacementStrategy).placeOrder(order);
        } finally {
            lock.unlock();
        }
    }

    private Order getOrder(OrderDTO orderDTO, Stock boughtStock, Stock soldStock) {
        Order order = new Order();

        if (orderDTO instanceof OrderDtoWithId) {
            order.setOrderType(orderDTO.getOrderType());
        }

        order.setUserId(orderDTO.getUserId());
        order.setSoldStock(boughtStock);
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        order.setBoughtStock(soldStock);
        order.setTimestamp(System.currentTimeMillis());
        return order;
    }
}
