package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException;
import com.javazerozahar.stock_exchange.exceptions.OrderNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.dto.OrderDtoWithId;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
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
    public void placeOrder(Portfolio portfolio, Stock stock, OrderDTO orderDTO) {

        if (!(orderDTO instanceof OrderDtoWithId)) {
            throw new ClassCastException("The order must be of type UpdateOrDeleteOrderDTO");
        }

        double orderValue = orderDTO.getQuantity() * orderDTO.getPrice();
        double availableAmount = portfolio.getQuantity() * stock.getPrice();

        orderRepository
                .findById(((OrderDtoWithId) orderDTO).getOrderId())
                .ifPresentOrElse(previousOrder -> {

                            double previousOrderValue = previousOrder.getQuantity() * previousOrder.getPrice();
                            double priceDifference = orderValue - previousOrderValue;

                            if (availableAmount > priceDifference) {
                                throw new InsufficientFundsException();
                            }

                            portfolio.setQuantity(availableAmount - priceDifference);

                        },
                        OrderNotFoundException::new
                );

        if (orderValue > availableAmount) {
            throw new InsufficientFundsException();
        }

        portfolioRepository.save(portfolio);
        orderRepository.save(getOrder(orderDTO, portfolio, stock));
    }

    private Order getOrder(OrderDTO orderDTO, Portfolio portfolio, Stock stock) {
        Order order = new Order();
        order.setOrderId(((OrderDtoWithId)orderDTO).getOrderId());
        order.setOrderType(orderDTO.getOrderType());
        order.setPortfolio(portfolio);
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        order.setStock(stock);
        return order;
    }
}
