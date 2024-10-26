package com.javazerozahar.stock_exchange.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyOrder implements Order{
    private Account buyer;
    private Double price;
    private Stock stock;
    private Integer quantity;

    @Override
    public boolean isValid() {
        return buyer.getBalance() >= price;
    }
}
