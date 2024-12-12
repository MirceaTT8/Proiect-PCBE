package com.javazerozahar.stock_exchange.model.dto;

import com.javazerozahar.stock_exchange.model.annotations.DtoId;
import com.javazerozahar.stock_exchange.model.annotations.Updatable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDTO {

    @DtoId
    private Long id;

    private Long stockId;

    @Updatable
    private Double quantity;

    private Long userId;
}
