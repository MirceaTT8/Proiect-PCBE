package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import com.javazerozahar.stock_exchange.repository.StockHistoryRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockHistoryRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;

    public StockHistoryService() {
        this.stockHistoryRepository = SingletonFactory.getInstance(StockHistoryRepositoryImpl.class);
    }

    public List<StockHistory> getStockHistoryOfStock(Long stockId) {
        return stockHistoryRepository.findByStockId(stockId);
    }
}
