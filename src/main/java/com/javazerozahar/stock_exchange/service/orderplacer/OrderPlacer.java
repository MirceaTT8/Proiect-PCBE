package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.converters.OrderConverter;
import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.rabbit.order.OrderPlacerProducer;
import com.javazerozahar.stock_exchange.service.StockService;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class OrderPlacer {

    private final StockService stockService;
    private final OrderPlacerProducer orderPlacerProducer;

    private final Map<String, OrderPlacementStrategy> orderPlacementStrategies;

    public OrderPlacer() {
        this.orderPlacerProducer = SingletonFactory.getInstance(OrderPlacerProducer.class);
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

        orderPlacerProducer.sendOrder(OrderConverter.toOrderDTO(order), orderPlacementStrategy);
        System.out.println("Order placed and sent to queue for user: " + order.getUserId());

        return null;
    }

    private Order getOrder(OrderDTO orderDTO, Stock boughtStock, Stock soldStock) {
        Order order = new Order();

        order.setOrderId(orderDTO.getOrderId());
        order.setUserId(orderDTO.getUserId());
        order.setSoldStock(soldStock);
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        order.setBoughtStock(boughtStock);
        order.setOrderType(orderDTO.getOrderType());
        order.setTimestamp(System.currentTimeMillis());
        return order;
    }
}
