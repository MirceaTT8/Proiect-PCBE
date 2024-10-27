package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.StockDTO;
import com.javazerozahar.stock_exchange.model.entity.Stock;

public class StockConverter {
    public Stock toStock(StockDTO stockDTO) {
        return new Stock(stockDTO.getId(), stockDTO.getSymbol(), stockDTO.getPrice(), stockDTO.getQuantity());
    }
    public StockDTO toStockDTO(Stock stock) {
        return new StockDTO(stock.getId(), stock.getSymbol(), stock.getPrice(), stock.getQuantity());
    }
}
