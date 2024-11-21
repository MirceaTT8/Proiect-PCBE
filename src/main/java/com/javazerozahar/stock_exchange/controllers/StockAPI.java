package com.javazerozahar.stock_exchange.controllers;

import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@CrossOrigin(origins = "http://localhost:8080")
public class StockAPI {
    @Autowired
    private StockService stockService;

    @GetMapping("/")
    @ResponseBody
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{stockId}")
    public Stock getStock(@PathVariable("stockId") Long stockId) { return stockService.getStock(stockId); }

    @PostMapping("/add/")
    public void addStock(@RequestBody Stock stock) { stockService.addStock(stock); }

    @PostMapping("/update/")
    public void updateStock(@RequestBody Stock stock) {
        stockService.updateStock(stock);
    }
}
