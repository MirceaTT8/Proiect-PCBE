package com.javazerozahar.stock_exchange.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long orderId;
    private Long userId;
    private Double price;
    private Long soldStockId;
    private Long boughtStockId;
    private Double quantity;
    private OrderType orderType;
    private Long timestamp;
}
