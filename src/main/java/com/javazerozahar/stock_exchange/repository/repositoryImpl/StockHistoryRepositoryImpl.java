package com.javazerozahar.stock_exchange.repository.repositoryImpl;

import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import com.javazerozahar.stock_exchange.repository.StockHistoryRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StockHistoryRepositoryImpl implements StockHistoryRepository {
    private final Map<Long, List<StockHistory>> stockHistories = new ConcurrentHashMap<>();

    @Override
    public void save(StockHistory stockHistory) {
        stockHistories.computeIfAbsent(stockHistory.getStockId(), k -> new ArrayList<>()).add(stockHistory);
    }

    @Override
    public List<StockHistory> findByStockId(Long stockId) {
        return stockHistories.getOrDefault(stockId, new ArrayList<>());
    }
}