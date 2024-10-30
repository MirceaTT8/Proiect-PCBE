package com.javazerozahar.stock_exchange.model.dto;

public class StockDTO {
    private Long id;
    private String symbol;
    private Double price;

    public StockDTO(Long id, String symbol, Double price) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
