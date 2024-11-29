package com.javazerozahar.stock_exchange.controllers;

import com.javazerozahar.stock_exchange.model.dto.StockDTO;
import com.javazerozahar.stock_exchange.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
public class StockController {

    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        return new ResponseEntity<>(stockService.getAllStocks(), HttpStatus.OK);
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<StockDTO> getStock(@PathVariable("stockId") Long stockId) {
        return new ResponseEntity<>(stockService.getStockDTO(stockId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StockDTO> addStock(@RequestBody StockDTO stockDTO) {
        return new ResponseEntity<>(stockService.addStock(stockDTO), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<StockDTO> updateStock(@RequestBody StockDTO stockDTO) {
        return new ResponseEntity<>(stockService.updateStock(stockDTO), HttpStatus.CREATED);
    }
}
