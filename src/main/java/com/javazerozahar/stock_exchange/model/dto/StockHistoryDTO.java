package com.javazerozahar.stock_exchange.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockHistoryDTO {
    private Long stockId;
    private Double price;
    private Long timestamp;

    public StockHistoryDTO(Long stockId, Double price, Long timestamp) {
        this.stockId = stockId;
        this.price = price;
        this.timestamp = timestamp;
    }

}
