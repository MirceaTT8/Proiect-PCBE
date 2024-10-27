package com.javazerozahar.stock_exchange.service.orderplacer;

import com.javazerozahar.stock_exchange.exceptions.PortfolioNotFoundException;
import com.javazerozahar.stock_exchange.exceptions.StockNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.PortfolioRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

import java.util.HashMap;
import java.util.Map;

public class OrderPlacer {

    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;

    private final Map<String, OrderPlacementStrategy> orderPlacementStrategies;

    public OrderPlacer() {
        this.portfolioRepository = SingletonFactory.getInstance(PortfolioRepositoryImpl.class);
        this.stockRepository = SingletonFactory.getInstance(StockRepositoryImpl.class);

        this.orderPlacementStrategies = new HashMap<>();
        orderPlacementStrategies.put("create", SingletonFactory.getInstance(CreateOrderPlacementStrategy.class));
        orderPlacementStrategies.put("update", SingletonFactory.getInstance(UpdateOrderPlacementStrategy.class));
        orderPlacementStrategies.put("delete", SingletonFactory.getInstance(DeleteOrderPlacementStrategy.class));
    }

    /**
     * @throws com.javazerozahar.stock_exchange.exceptions.InsufficientFundsException if the criteria aren't met
     */
    public void placeOrder(OrderDTO orderDTO, String orderPlacementStrategy) {

        Portfolio portfolio = portfolioRepository.findById(orderDTO.getPortfolioId())
                .orElseThrow(PortfolioNotFoundException::new);

        Stock stock = stockRepository.findById(orderDTO.getStockId())
                .orElseThrow(() -> new StockNotFoundException(orderDTO.getStockId()));

        orderPlacementStrategies.get(orderPlacementStrategy).placeOrder(portfolio, stock, orderDTO);
    }


}
