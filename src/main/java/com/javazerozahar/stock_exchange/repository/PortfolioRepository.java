package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Portfolio;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository {
    void save(Portfolio portfolio);
    Optional<Portfolio> findById(Long id);
    List<Portfolio> findAllByAccountId(Long accountId);
    void update(Portfolio portfolio);
    void deleteById(Long id);
}