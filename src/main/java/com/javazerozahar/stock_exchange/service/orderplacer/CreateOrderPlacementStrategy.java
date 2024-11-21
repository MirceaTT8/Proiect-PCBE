package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException;
import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.service.PortfolioService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class CreateOrderPlacementStrategy implements OrderPlacementStrategy {

    private final OrderRepository orderRepository;
    private final PortfolioService portfolioService;

    @Override
    public Order placeOrder(Order order) {

        Portfolio portfolio = portfolioService.getPortfolioByUserIdAndStock(order.getUser().getId(), order.getSoldStock());

        double orderValue = order.getQuantity() * (order.getOrderType().equals(OrderType.BUY) ? order.getPrice() : 1);
        double availableAmount = portfolio.getQuantity();

        if (orderValue > availableAmount) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        portfolio.setQuantity(availableAmount - orderValue);

        order = orderRepository.save(order);
        portfolioService.save(portfolio);

        log.info("User {} created order {}\n with value {}\nHas portfolio {}", order.getUser().getId(), order, orderValue, portfolio);

        return order;
    }

}
