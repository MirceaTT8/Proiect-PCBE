package com.javazerozahar.stock_exchange.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionDTO {
    private Long id;
    private Long stockId;
    private Long sellerId;
    private Long buyerId;
    private Double quantity;
    private Double price;
    private Long timestamp;

    public TransactionDTO(Long id, Long stockId, Long sellerId, Long buyerId, Double quantity, Double price, Long timestamp) {
        this.id = id;
        this.stockId = stockId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

}
