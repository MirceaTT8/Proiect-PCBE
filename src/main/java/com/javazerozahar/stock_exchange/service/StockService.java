package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.converters.StockConverter;
import com.javazerozahar.stock_exchange.exceptions.StockNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.StockDTO;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockConverter stockConverter;

    public Stock getStock(Long stockId) {
        return stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException(stockId));
    }

    public StockDTO getStockDTO(Long stockId) {
        return stockConverter.toStockDTO(getStock(stockId));
    }

    public List<StockDTO> getAllStocks() {
        return stockRepository.findAll().stream().map(stockConverter::toStockDTO).toList();
    }

    public void addStock(StockDTO stockDTO) {
        stockRepository.save(stockConverter.toStock(stockDTO));
    }

    public void updateStock(StockDTO stockDTO) {

        // TODO Patch mapping

        stockRepository.save(stockConverter.toStock(stockDTO));
    }
}
