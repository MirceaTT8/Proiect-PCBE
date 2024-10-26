package com.javazerozahar.stock_exchange.model.dto;

public class StockHistoryDTO {
    private Long stockId;
    private Double price;
    private Long timestamp;

    public StockHistoryDTO(Long stockId, Double price, Long timestamp) {
        this.stockId = stockId;
        this.price = price;
        this.timestamp = timestamp;
    }

    public Long getStockId() {
        return stockId;
    }

    public Double getPrice() {
        return price;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
