package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.OrderRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.PortfolioRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

public class CreateOrderPlacementStrategy implements OrderPlacementStrategy {

    private final OrderRepository orderRepository;
    private final PortfolioRepository portfolioRepository;

    public CreateOrderPlacementStrategy() {
        this.orderRepository = SingletonFactory.getInstance(OrderRepositoryImpl.class);
        this.portfolioRepository = SingletonFactory.getInstance(PortfolioRepositoryImpl.class);
    }

    @Override
    public Order placeOrder(Order order) {

        Portfolio portfolio = portfolioRepository.findByUserIdAndStock(order.getUserId(), order.getSoldStock());

        double orderValue = order.getQuantity() * order.getPrice();
        double availableAmount = portfolio.getQuantity() * order.getBoughtStock().getPrice();

        if (orderValue > availableAmount) {
            throw new InsufficientFundsException();
        }

        portfolio.setQuantity(availableAmount - orderValue);

        orderRepository.save(order);
        portfolioRepository.save(portfolio);

        return order;
    }

}
