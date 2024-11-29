package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.StockHistoryDTO;
import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import com.javazerozahar.stock_exchange.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StockHistoryConverter {

    private final StockService stockService;

    public StockHistory toStockHistory(StockHistoryDTO stockHistoryDTO) {
        return new StockHistory(
                stockHistoryDTO.getId(),
                stockService.getStock(stockHistoryDTO.getStockId()),
                stockHistoryDTO.getPrice(),
                stockHistoryDTO.getTimestamp());
    }
    public StockHistoryDTO toStockHistoryDTO(StockHistory stockHistory) {
        return new StockHistoryDTO(
                stockHistory.getId(),
                stockHistory.getStock().getId(),
                stockHistory.getPrice(),
                stockHistory.getTimestamp());
    }

}
