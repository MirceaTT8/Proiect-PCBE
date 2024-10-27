package com.javazerozahar.stock_exchange.model.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long userId;
    private Long portfolioId;
    private Long stockId;
    private Double price;
    private Double quantity;
    private OrderType orderType;
}
