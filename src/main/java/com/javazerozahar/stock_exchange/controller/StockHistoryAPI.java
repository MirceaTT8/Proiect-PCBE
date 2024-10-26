package com.javazerozahar.stock_exchange.controller;

import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import com.javazerozahar.stock_exchange.repository.StockHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stock_history")
@CrossOrigin(origins = "http://localhost:4200")

public class StockHistoryAPI {
    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @GetMapping("/get/{id}")
    public List<StockHistory> findByStockId(@PathVariable("id") Long id) { return stockHistoryRepository.findByStockId(id); }
}
