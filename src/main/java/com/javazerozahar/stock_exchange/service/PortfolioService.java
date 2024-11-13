package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.exceptions.PortfolioNotFoundException;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.PortfolioRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioService() {
        this.portfolioRepository = SingletonFactory.getInstance(PortfolioRepositoryImpl.class);
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public List<Portfolio> getPortfoliosByUser(Long userId) {
        return portfolioRepository.findAllByUserId(userId);
    }

    public Portfolio getPortfolioByUserIdAndStock(Long userId, Stock stock) {
        return portfolioRepository.findByUserIdAndStock(userId, stock)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found for " + userId + " : " + stock));
    }

    public Portfolio updatePortfolio(Order order, double quantity) {
        Portfolio portfolio = getPortfolioByUserIdAndStock(order.getUserId(), order.getBoughtStock());
        portfolio.setQuantity(portfolio.getQuantity() + quantity);
        portfolioRepository.save(portfolio);
        return portfolio;
    }

    public void save(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }
}
