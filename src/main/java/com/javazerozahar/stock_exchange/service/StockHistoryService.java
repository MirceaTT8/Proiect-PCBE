package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import com.javazerozahar.stock_exchange.repository.StockHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;

    public List<StockHistory> getStockHistoryOfStock(Long stockId) {
        return stockHistoryRepository.findByStockId(stockId);
    }
}
