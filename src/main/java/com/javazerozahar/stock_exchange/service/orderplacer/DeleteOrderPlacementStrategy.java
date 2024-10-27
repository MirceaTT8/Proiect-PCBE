package com.javazerozahar.stock_exchange.service.orderplacer;

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

public class DeleteOrderPlacementStrategy implements OrderPlacementStrategy {

    private final OrderRepository orderRepository;
    private final PortfolioRepository portfolioRepository;

    public DeleteOrderPlacementStrategy() {
        this.orderRepository = SingletonFactory.getInstance(OrderRepositoryImpl.class);
        this.portfolioRepository = SingletonFactory.getInstance(PortfolioRepositoryImpl.class);
    }

    @Override
    public void placeOrder(Portfolio portfolio, Stock stock, OrderDTO orderDTO) {

        if (!(orderDTO instanceof OrderDtoWithId)) {
            throw new ClassCastException("The order must be of type UpdateOrDeleteOrderDTO");
        }

        double availableAmount = portfolio.getQuantity() * stock.getPrice();

        orderRepository
                .findById(((OrderDtoWithId) orderDTO).getOrderId())
                .ifPresentOrElse(previousOrder -> {

                            double previousOrderValue = previousOrder.getQuantity() * previousOrder.getPrice();
                            portfolio.setQuantity(availableAmount + previousOrderValue);

                        },
                        OrderNotFoundException::new
                );

        portfolioRepository.save(portfolio);
        orderRepository.remove(getOrder(orderDTO));
    }

    private Order getOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderId(((OrderDtoWithId)orderDTO).getOrderId());
        return order;
    }
}
