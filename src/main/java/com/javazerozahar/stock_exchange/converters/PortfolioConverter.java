package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.PortfolioDTO;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.service.StockService;
import com.javazerozahar.stock_exchange.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PortfolioConverter {

    private final UserService userService;
    private final StockService stockService;

    public Portfolio toPortfolio(PortfolioDTO portfolioDTO) {
        return new Portfolio(
                portfolioDTO.getId(),
                userService.getUser(portfolioDTO.getUserId()),
                stockService.getStock(portfolioDTO.getStockId()),
                portfolioDTO.getQuantity()
        );
    }
    public PortfolioDTO toPortfolioDTO(Portfolio portfolio) {
        return new PortfolioDTO(
                portfolio.getId(),
                portfolio.getStock().getId(),
                portfolio.getQuantity(),
                portfolio.getUser().getId()
        );
    }
}
