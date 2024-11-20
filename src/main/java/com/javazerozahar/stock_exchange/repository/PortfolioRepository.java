package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends Resettable, JpaRepository<Portfolio, Long> {
    Portfolio save(Portfolio portfolio);
    Optional<Portfolio> findById(Long id);
    List<Portfolio> findAllByUserId(Long accountId);
    void update(Portfolio portfolio);
    void deleteById(Long id);
    List<Portfolio> findAll();

    Optional<Portfolio> findByUserIdAndStock(Long userId, Stock stock);
}