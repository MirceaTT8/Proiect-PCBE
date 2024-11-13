package com.javazerozahar.stock_exchange.model.dto;

import com.javazerozahar.stock_exchange.model.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMessageDTO {
    private OrderDTO order;
    private String orderPlacementStrategy;
}
