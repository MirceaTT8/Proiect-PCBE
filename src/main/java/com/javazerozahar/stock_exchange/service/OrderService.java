package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.converters.OrderConverter;
import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.service.orderplacer.OrderPlacer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderPlacer orderPlacer;
    private final OrderMatcher orderMatcher;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    @Transactional
    public void placeOrder(OrderDTO orderDTO, String orderStrategy) {

        Order order = orderPlacer.placeOrder(orderConverter.toOrder(orderDTO), orderStrategy);
        orderMatcher.matchOrder(order);
    }

    public List<OrderDTO> getAllOrders(Long stockId, String orderType) {
        if (stockId == null && orderType == null) {
            return orderRepository.findAll().stream().map(orderConverter::toOrderDTO).toList();
        }

        if (stockId != null && orderType == null) {
            return orderRepository.findAllByStockId(stockId).stream().map(orderConverter::toOrderDTO).toList();
        }

        if (stockId == null) {
            throw new RuntimeException("Stock id must be provided");
        }

        if (orderType.compareToIgnoreCase(OrderType.BUY.name()) == 0) {
            return orderRepository.findBuyByStockId(stockId).stream().map(orderConverter::toOrderDTO).toList();
        } else if (orderType.compareToIgnoreCase(OrderType.SELL.name()) == 0) {
            return orderRepository.findSellByStockId(stockId).stream().map(orderConverter::toOrderDTO).toList();
        } else {
            throw new RuntimeException("Unknown order type");
        }

    }

    public Optional<Order> getOrder(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<OrderDTO> getOrdersByUser(Long userId, Long stockId) {
        if (stockId != null) {
            return orderRepository.findByUserIdStockId(userId, stockId).stream().map(orderConverter::toOrderDTO).toList();
        }

        return orderRepository.findByUserId(userId).stream().map(orderConverter::toOrderDTO).toList();
    }

}
