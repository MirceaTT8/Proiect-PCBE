package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException;
import com.javazerozahar.stock_exchange.exceptions.OrderNotFoundException;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.OrderRepositoryImpl;
import com.javazerozahar.stock_exchange.service.PortfolioService;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UpdateOrderPlacementStrategy implements OrderPlacementStrategy {

    private final OrderRepository orderRepository;
    private final PortfolioService portfolioService;

    public UpdateOrderPlacementStrategy() {
        this.orderRepository = SingletonFactory.getInstance(OrderRepositoryImpl.class);
        this.portfolioService = SingletonFactory.getInstance(PortfolioService.class);
    }

    @Override
    public Order placeOrder(Order order) {

        Portfolio portfolio = portfolioService.getPortfolioByUserIdAndStock(order.getUserId(), order.getSoldStock());

        double orderValue = order.getQuantity() * order.getPrice();
        double availableAmount = portfolio.getQuantity() * order.getSoldStock().getPrice();

        orderRepository
                .findById(order.getOrderId())
                .ifPresentOrElse(previousOrder -> {

                            double previousOrderValue = previousOrder.getQuantity() * previousOrder.getPrice();
                            double priceDifference = orderValue - previousOrderValue;

                            if (availableAmount < priceDifference) {
                                throw new InsufficientFundsException("Insufficient funds");
                            }

                            portfolio.setQuantity(availableAmount - priceDifference);

                        },
                        OrderNotFoundException::new
                );

        if (orderValue > availableAmount) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        portfolioService.save(portfolio);
        orderRepository.save(order);

        log.info("User {} updated order {}\n with value {}\nHas portfolio {}", order.getUserId(), order, orderValue, portfolio);

        return order;
    }

}
