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
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class StockHistoryController {
    private StockHistoryService stockHistoryService;

    @GetMapping("/{stockId}")
    @ResponseBody
    public ResponseEntity<List<StockHistoryDTO>> getStockHistoryOfStock(
            @PathVariable("stockId") Long stockId,
            @RequestParam(required = false) Integer days
    ) {
        return new ResponseEntity<>(stockHistoryService.getStockHistoryOfStock(stockId, days), HttpStatus.OK);
    }
}
