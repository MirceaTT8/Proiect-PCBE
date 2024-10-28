package com.javazerozahar.stock_exchange.model.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long userId;
    private Long soldStockId;
    private Long boughtStockId;
    private Double price;
    private Double quantity;
    private OrderType orderType;
    private Long timestamp;
}
