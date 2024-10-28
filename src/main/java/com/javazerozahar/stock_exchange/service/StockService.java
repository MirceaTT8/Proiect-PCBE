package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.exceptions.StockNotFoundException;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

public class StockService {

    private final StockRepository stockRepository;

    public StockService() {
        this.stockRepository = SingletonFactory.getInstance(StockRepositoryImpl.class);
    }

    public Stock getStock(Long stockId) {
        return stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException(stockId));
    }
}
