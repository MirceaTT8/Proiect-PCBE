package com.javazerozahar.stock_exchange.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockDTO {
    private Long id;
    private String symbol;
    private Double price;

    public StockDTO(Long id, String symbol, Double price) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
    }

}
