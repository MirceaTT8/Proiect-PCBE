package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException;
import com.javazerozahar.stock_exchange.exceptions.OrderNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.service.PortfolioService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Log4j2
public class UpdateOrderPlacementStrategy implements OrderPlacementStrategy {

    private final OrderRepository orderRepository;
    private final PortfolioService portfolioService;

    @Override
    @Transactional
    public Order placeOrder(Order order) {

        Portfolio portfolio = portfolioService.getPortfolioByUserIdAndStock(order.getUser().getId(), order.getSoldStock());

        double orderValue = order.getQuantity() * (order.getOrderType().equals(OrderType.BUY) ? order.getPrice() : 1);
        double availableAmount = portfolio.getQuantity();

        orderRepository
                .findById(order.getOrderId())
                .ifPresentOrElse(previousOrder -> {

                            double previousOrderValue = previousOrder.getQuantity() * (previousOrder.getOrderType().equals(OrderType.BUY) ? previousOrder.getPrice() : 1);
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

        log.info("User {} updated order {}\n with value {}\nHas portfolio {}", order.getUser().getId(), order, orderValue, portfolio);

        return order;
    }

}
