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
public class SellOrder implements Order{
    private Account seller;
    private Stock stock;
    private Integer quantity;
    private Double price;

    @Override
    public boolean isValid() {
        List<Portfolio> portfolioList = seller.getPortfolio();
        for (Portfolio p : portfolioList) {
            if (p.getStockId() == stock.getId()) {
                if (p.getQuantity() >= quantity) return true;
            }
        }
        return false;
    }
}
