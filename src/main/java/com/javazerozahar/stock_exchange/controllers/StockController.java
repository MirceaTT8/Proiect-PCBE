package com.javazerozahar.stock_exchange.controllers;

import com.javazerozahar.stock_exchange.model.dto.StockDTO;
import com.javazerozahar.stock_exchange.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
public class StockController {

    private StockService stockService;

    @GetMapping
    public List<StockDTO> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{stockId}")
    public StockDTO getStock(@PathVariable("stockId") Long stockId) {
        return stockService.getStockDTO(stockId);
    }

    @PostMapping
    public void addStock(@RequestBody StockDTO stockDTO) {
        stockService.addStock(stockDTO);
    }

    @PatchMapping
    public void updateStock(@RequestBody StockDTO stockDTO) {
        stockService.updateStock(stockDTO);
    }
}
