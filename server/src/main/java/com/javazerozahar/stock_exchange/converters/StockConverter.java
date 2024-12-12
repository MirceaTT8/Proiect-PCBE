package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.StockDTO;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import org.springframework.stereotype.Service;

@Service
public class StockConverter {
    public Stock toStock(StockDTO stockDTO) {
        return new Stock(
                stockDTO.getId(),
                stockDTO.getSymbol(),
                stockDTO.getPrice(),
                true
        );
    }
    public StockDTO toStockDTO(Stock stock) {
        return new StockDTO(stock.getId(), stock.getSymbol(), stock.getPrice());
    }
}
