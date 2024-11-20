package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends Resettable, JpaRepository<Stock, Long> {
    Stock save(Stock stock);
    Optional<Stock> findById(Long id);
    List<Stock> findAll();
    void update(Stock stock);
    void deleteById(Long id);
}
