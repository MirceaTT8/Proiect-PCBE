package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException;
import com.javazerozahar.stock_exchange.exceptions.OrderNotFoundException;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.OrderRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.PortfolioRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

public class UpdateOrderPlacementStrategy implements OrderPlacementStrategy {

    private final OrderRepository orderRepository;
    private final PortfolioRepository portfolioRepository;

    public UpdateOrderPlacementStrategy() {
        this.orderRepository = SingletonFactory.getInstance(OrderRepositoryImpl.class);
        this.portfolioRepository = SingletonFactory.getInstance(PortfolioRepositoryImpl.class);
    }

    @Override
    public Order placeOrder(Order order) {

        Portfolio portfolio = portfolioRepository.findByUserIdAndStock(order.getUserId(), order.getSoldStock());

        double orderValue = order.getQuantity() * order.getPrice();
        double availableAmount = portfolio.getQuantity() * order.getBoughtStock().getPrice();

        orderRepository
                .findById(order.getOrderId())
                .ifPresentOrElse(previousOrder -> {

                            double previousOrderValue = previousOrder.getQuantity() * previousOrder.getPrice();
                            double priceDifference = orderValue - previousOrderValue;

                            if (availableAmount > priceDifference) {
                                throw new InsufficientFundsException("Insufficient funds");
                            }

                            portfolio.setQuantity(availableAmount - priceDifference);

                        },
                        OrderNotFoundException::new
                );

        if (orderValue > availableAmount) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        portfolioRepository.save(portfolio);
        orderRepository.save(order);

        return order;
    }

}
