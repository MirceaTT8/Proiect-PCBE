package com.javazerozahar.stock_exchange.model.dto;

public class TransactionDTO {
    private Long id;
    private Long stockId;
    private Long sellerId;
    private Long buyerId;
    private Integer quantity;
    private Double price;
    private Long timestamp;

    public TransactionDTO(Long id, Long stockId, Long sellerId, Long buyerId, Integer quantity, Double price, Long timestamp) {
        this.id = id;
        this.stockId = stockId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public Long getStockId() {
        return stockId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
