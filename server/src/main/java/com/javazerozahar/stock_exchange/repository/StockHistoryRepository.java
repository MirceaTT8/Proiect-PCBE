package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findByStockId(Long id);
}
