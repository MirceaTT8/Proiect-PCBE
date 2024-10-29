package com.javazerozahar.stock_exchange.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDTO {
    private Long id;
    private Long stockId;
    private Double quantity;
    private Long userId;
}
