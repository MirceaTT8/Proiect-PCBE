package com.javazerozahar.stock_exchange.model.dto;

import com.javazerozahar.stock_exchange.model.annotations.DtoId;
import com.javazerozahar.stock_exchange.model.annotations.Updatable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    @DtoId
    private Long orderId;

    private Long userId;

    @Updatable
    private Double price;

    @Updatable
    private Long soldStockId;

    @Updatable
    private Long boughtStockId;

    @Updatable
    private Double quantity;

    @Updatable
    private OrderType orderType;

    private Long timestamp;
}
