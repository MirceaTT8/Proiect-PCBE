package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.exceptions.StockNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.dto.OrderDtoWithId;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.PortfolioRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

import java.util.HashMap;
import java.util.Map;

public class OrderPlacer {

    private final StockRepository stockRepository;

    private final Map<String, OrderPlacementStrategy> orderPlacementStrategies;

    public OrderPlacer() {
        this.stockRepository = SingletonFactory.getInstance(StockRepositoryImpl.class);

        this.orderPlacementStrategies = new HashMap<>();
        orderPlacementStrategies.put("create", SingletonFactory.getInstance(CreateOrderPlacementStrategy.class));
        orderPlacementStrategies.put("update", SingletonFactory.getInstance(UpdateOrderPlacementStrategy.class));
        orderPlacementStrategies.put("delete", SingletonFactory.getInstance(DeleteOrderPlacementStrategy.class));
    }

    /**
     * @throws com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException if the criteria aren't met
     */
    public Order placeOrder(OrderDTO orderDTO, String orderPlacementStrategy) {

        Stock soldStock = stockRepository.findById(orderDTO.getSoldStockId())
                .orElseThrow(() -> new StockNotFoundException(orderDTO.getSoldStockId()));

        Stock boughtStock = stockRepository.findById(orderDTO.getBoughtStockId())
                .orElseThrow(() -> new StockNotFoundException(orderDTO.getBoughtStockId()));

        return placeOrder(getOrder(orderDTO, boughtStock, soldStock), orderPlacementStrategy);
    }

    public Order placeOrder(Order order, String orderPlacementStrategy) {
        return orderPlacementStrategies.get(orderPlacementStrategy).placeOrder(order);
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
