package com.javazerozahar.stock_exchange.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long id;
    private Long stockId;
    private Long sellerId;
    private Long buyerId;
    private Integer quantity;
    private Double price;
    private Long timestamp;
}