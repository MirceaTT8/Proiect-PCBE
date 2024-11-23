package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.converters.StockHistoryConverter;
import com.javazerozahar.stock_exchange.model.dto.StockHistoryDTO;
import com.javazerozahar.stock_exchange.repository.StockHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;
    private final StockHistoryConverter stockHistoryConverter;

    public List<StockHistoryDTO> getStockHistoryOfStock(Long stockId) {
        return stockHistoryRepository.findByStockId(stockId).stream().map(stockHistoryConverter::toStockHistoryDTO).toList();
    }
}
