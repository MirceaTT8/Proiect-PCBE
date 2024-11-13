package com.javazerozahar.stock_exchange.RestAPIs;

import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import com.javazerozahar.stock_exchange.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/stockHistory")
@CrossOrigin(origins = "http://localhost:8080")
public class StockHistoryAPI {
    @Autowired
    private StockHistoryService stockHistoryService;

    @GetMapping("/{stockId}")
    @ResponseBody
    public List<StockHistory> getStockHistoryOfStock(@PathVariable("stockId") Long stockId) {
        return stockHistoryService.getStockHistoryOfStock(stockId);
    }
}
