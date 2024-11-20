package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.StockHistoryDTO;
import com.javazerozahar.stock_exchange.model.entity.StockHistory;

public class StockHistoryConverter {
    public StockHistory toStockHistory(StockHistoryDTO stockHistoryDTO) {
        return new StockHistory(stockHistoryDTO.getId(), stockHistoryDTO.getStockId(), stockHistoryDTO.getPrice(), stockHistoryDTO.getTimestamp());
    }
    public StockHistoryDTO toStockHistoryDTO(StockHistory stockHistory) {
        return new StockHistoryDTO(stockHistory.getId(), stockHistory.getStockId(), stockHistory.getPrice(), stockHistory.getTimestamp());
    }

}
