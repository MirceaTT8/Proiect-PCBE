package com.javazerozahar.stock_exchange.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {
    private Long id;
    private Long stockId;
    private Integer quantity;
}