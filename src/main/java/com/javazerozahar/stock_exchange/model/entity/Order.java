package com.javazerozahar.stock_exchange.model.entity;

import com.javazerozahar.stock_exchange.model.dto.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long orderId;
    private Portfolio portfolio;
    private Double price;
    private Stock stock;
    private Double quantity;
    private OrderType orderType;
}
