package com.javazerozahar.stock_exchange.controllers;

import com.javazerozahar.stock_exchange.model.dto.StockDTO;
import com.javazerozahar.stock_exchange.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stocks")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class StockController {

    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks(@RequestParam(value = "q", required = false) String query) {
        List<StockDTO> stocks;

        if (query != null && !query.isEmpty()) {
            stocks = stockService.getAllStocks().stream()
                    .filter(stock -> stock.getName().toLowerCase().contains(query.toLowerCase()) ||
                            stock.getSymbol().toLowerCase().contains(query.toLowerCase()))
                    .limit(5)
                    .collect(Collectors.toList());
        } else {
            stocks = new ArrayList<>(stockService.getAllStocks());
        }

        return new ResponseEntity<>(stocks, HttpStatus.OK);
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

    @DeleteMapping("/{stockId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStock(@PathVariable("stockId") Long stockId) {
        stockService.deleteStock(stockId);
    }
}
