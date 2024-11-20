package com.javazerozahar.stock_exchange.repository;
import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockHistoryRepository extends Resettable, JpaRepository<StockHistory, Long> {
    StockHistory save(StockHistory stockHistory);
    List<StockHistory> findByStockId(Long stockId);
}
