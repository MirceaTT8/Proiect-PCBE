package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.PortfolioDTO;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;

public class PortfolioConverter {
    public Portfolio toPortfolio(PortfolioDTO portfolioDTO) {
        return new Portfolio(
                portfolioDTO.getId(),
                portfolioDTO.getUserId(),
                portfolioDTO.getStockId(),
                portfolioDTO.getQuantity()
        );
    }
    public PortfolioDTO toPortfolioDTO(Portfolio portfolio) {
        return new PortfolioDTO(
                portfolio.getId(),
                portfolio.getStockId(),
                portfolio.getQuantity(),
                portfolio.getUserId()
        );
    }
}
