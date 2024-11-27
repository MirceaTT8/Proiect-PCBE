package com.javazerozahar.stock_exchange.controllers;

import com.javazerozahar.stock_exchange.model.dto.StockHistoryDTO;
import com.javazerozahar.stock_exchange.service.StockHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/stock-history")
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
public class StockHistoryController {
    private StockHistoryService stockHistoryService;

    @GetMapping("/{stockId}")
    @ResponseBody
    public ResponseEntity<List<StockHistoryDTO>> getStockHistoryOfStock(@PathVariable("stockId") Long stockId) {
        return new ResponseEntity<>(stockHistoryService.getStockHistoryOfStock(stockId), HttpStatus.OK);
    }
}
