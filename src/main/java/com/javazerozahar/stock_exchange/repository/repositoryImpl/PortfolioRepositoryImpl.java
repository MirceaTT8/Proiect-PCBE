package com.javazerozahar.stock_exchange.repository.repositoryImpl;

import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;

import java.util.List;
import java.util.Optional;

public class PortfolioRepositoryImpl implements PortfolioRepository {

    @Override
    public void save(Portfolio portfolio) {

    }

    @Override
    public Optional<Portfolio> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Portfolio> findAllByUserId(Long accountId) {
        return List.of();
    }

    @Override
    public void update(Portfolio portfolio) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Portfolio> findAll() {
        return List.of();
    }

    @Override
    public Portfolio findByUserIdAndStock(Long userId, Stock stock) {
        return null;
    }
}
