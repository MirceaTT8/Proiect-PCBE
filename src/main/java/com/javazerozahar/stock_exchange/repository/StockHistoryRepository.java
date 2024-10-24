package com.javazerozahar.stock_exchange.repository;
import com.javazerozahar.stock_exchange.model.entity.StockHistory;

import java.util.List;

public interface StockHistoryRepository {
    void save(StockHistory stockHistory);
    List<StockHistory> findByStockId(Long stockId);
}
