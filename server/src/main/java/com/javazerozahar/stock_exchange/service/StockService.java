package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.converters.StockConverter;
import com.javazerozahar.stock_exchange.exceptions.StockNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.StockDTO;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import com.javazerozahar.stock_exchange.utils.Patcher;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class StockService {

    private final StockRepository stockRepository;
    private final StockConverter stockConverter;
    private final Patcher patcher;

    @Transactional(readOnly = true)
    public Stock getStock(Long stockId) {
        return stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException(stockId));
    }

    @Transactional(readOnly = true)
    public StockDTO getStockDTO(Long stockId) {
        return stockConverter.toStockDTO(getStock(stockId));
    }

    @Transactional(readOnly = true)
    public List<StockDTO> getAllStocks() {
        return stockRepository.findAll().stream()
                .map(stockConverter::toStockDTO)
                .toList();
    }

    @Transactional
    public StockDTO addStock(StockDTO stockDTO) {
        return stockConverter.toStockDTO(stockRepository.save(stockConverter.toStock(stockDTO)));
    }

    @Transactional
    public StockDTO updateStock(StockDTO stockDTO) {
        StockDTO stockToBeUpdated = getStockDTO(stockDTO.getId());
        Stock stock = stockConverter.toStock(patcher.patch(stockToBeUpdated, stockDTO));
        stockRepository.save(stock);
        return stockConverter.toStockDTO(stock);
    }
}