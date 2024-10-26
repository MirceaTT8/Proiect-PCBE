package com.javazerozahar.stock_exchange.model.dto;

public class PortfolioDTO {
    private Long id;
    private Long stockId;
    private Integer quantity;

    public PortfolioDTO(Long id, Long stockId, Integer quantity) {
        this.id = id;
        this.stockId = stockId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getStockId() {
        return stockId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
