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
    private Long userId;
    private Double price;
    private Stock soldStock;
    private Stock boughtStock;
    private Double quantity;
    private OrderType orderType;
    private Long timestamp;
}
