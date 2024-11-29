package com.javazerozahar.stock_exchange.model.dto;

import com.javazerozahar.stock_exchange.model.annotations.DtoId;
import com.javazerozahar.stock_exchange.model.annotations.Updatable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StockDTO {

    @DtoId
    private Long id;

    @Updatable
    private String symbol;

    @Updatable
    private Double price;

    public StockDTO(Long id, String symbol, Double price) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
    }

}
