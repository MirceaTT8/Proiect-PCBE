package com.javazerozahar.stock_exchange.repository;
import com.javazerozahar.stock_exchange.model.entity.StockHistory;

import java.util.List;

public interface StockHistoryRepository extends Resettable {
    void save(StockHistory stockHistory);
    List<StockHistory> findByStockId(Long stockId);
}
