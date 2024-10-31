package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Stock;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends Resetable {
    void save(Stock stock);
    Optional<Stock> findById(Long id);
    List<Stock> findAll();
    void update(Stock stock);
    void deleteById(Long id);
}
