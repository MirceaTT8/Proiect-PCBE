package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.exceptions.OrderNotFoundException;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.OrderRepositoryImpl;
import com.javazerozahar.stock_exchange.service.PortfolioService;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

public class DeleteOrderPlacementStrategy implements OrderPlacementStrategy {

    private final OrderRepository orderRepository;
    private final PortfolioService portfolioService;

    public DeleteOrderPlacementStrategy() {
        this.orderRepository = SingletonFactory.getInstance(OrderRepositoryImpl.class);
        this.portfolioService = SingletonFactory.getInstance(PortfolioService.class);
    }

    @Override
    public Order placeOrder(Order order) {

        Portfolio portfolio = portfolioService.getPortfolioByUserIdAndStock(order.getUserId(), order.getSoldStock());

        double availableAmount = portfolio.getQuantity() * order.getBoughtStock().getPrice();

        orderRepository
                .findById(order.getOrderId())
                .ifPresentOrElse(previousOrder -> {

                            double previousOrderValue = previousOrder.getQuantity() * previousOrder.getPrice();
                            portfolio.setQuantity(availableAmount + previousOrderValue);

                        },
                        OrderNotFoundException::new
                );

        portfolioService.save(portfolio);
        orderRepository.remove(order);

        return order;
    }

}
