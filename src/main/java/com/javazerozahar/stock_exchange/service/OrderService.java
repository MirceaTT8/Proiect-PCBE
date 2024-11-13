package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.OrderRepositoryImpl;
import com.javazerozahar.stock_exchange.service.orderplacer.OrderPlacer;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderPlacer orderPlacer;
    private final OrderMatcher orderMatcher;
    private final OrderRepository orderRepository;

    public OrderService() {
        this.orderPlacer = SingletonFactory.getInstance(OrderPlacer.class);
        this.orderMatcher = SingletonFactory.getInstance(OrderMatcher.class);
        this.orderRepository = SingletonFactory.getInstance(OrderRepositoryImpl.class);
    }

    public synchronized void placeOrder(OrderDTO orderDTO, String orderStrategy) {

        Order order = orderPlacer.placeOrder(orderDTO, orderStrategy);
        orderMatcher.matchOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrder(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getOrdersByBoughtStock(Stock stock) {
        return orderRepository.findByBoughtStock(stock);
    }

    public List<Order> getOrdersBySoldStock(Stock stock) {
        return orderRepository.findBySoldStock(stock);
    }

}
