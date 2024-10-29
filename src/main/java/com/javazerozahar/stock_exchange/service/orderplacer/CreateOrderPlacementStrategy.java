package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.OrderRepositoryImpl;
import com.javazerozahar.stock_exchange.service.PortfolioService;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

public class CreateOrderPlacementStrategy implements OrderPlacementStrategy {

    private final OrderRepository orderRepository;
    private final PortfolioService portfolioService;

    public CreateOrderPlacementStrategy() {
        this.orderRepository = SingletonFactory.getInstance(OrderRepositoryImpl.class);
        this.portfolioService = SingletonFactory.getInstance(PortfolioService.class);
    }

    @Override
    public Order placeOrder(Order order) {

        Portfolio portfolio = portfolioService.getPortfolioByUserIdAndStock(order.getUserId(), order.getSoldStock());

        double orderValue = order.getQuantity() * order.getPrice();
        double availableAmount = portfolio.getQuantity() * order.getBoughtStock().getPrice();

        if (orderValue > availableAmount) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        portfolio.setQuantity(availableAmount - orderValue);

        orderRepository.save(order);
        portfolioService.save(portfolio);

        return order;
    }

}
