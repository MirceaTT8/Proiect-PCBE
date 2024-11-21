package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.exceptions.StockNotFoundException;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public Stock getStock(Long stockId) {
        return stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException(stockId));
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public void addStock(Stock stock) {
        stockRepository.save(stock);
    }

    public void updateStock(Stock stock) {
        stockRepository.save(stock);
    }
}
