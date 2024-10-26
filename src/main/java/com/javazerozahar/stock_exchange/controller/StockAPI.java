package com.javazerozahar.stock_exchange.controller;

import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "http://localhost:4200")

public class StockAPI {
    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/")
    public List<Stock> findAllStocks() { return stockRepository.findAll(); }

    @GetMapping("/get/{id}")
    public Optional<Stock> findByStockById(@PathVariable("id") Long id) { return stockRepository.findById(id); }

    @PostMapping("/update")
    public void updateStock(@RequestBody Stock stock) { stockRepository.update(stock); }

    @DeleteMapping("/delete/{id}")
    public void deleteStock(@PathVariable("id") Long id) { stockRepository.deleteById(id); }
}
