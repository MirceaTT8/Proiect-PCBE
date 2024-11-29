package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.exceptions.UserNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.PortfolioDTO;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.User;
import com.javazerozahar.stock_exchange.repository.UserRepository;
import com.javazerozahar.stock_exchange.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PortfolioConverter {
    
    private final UserRepository userRepository;
    private final StockService stockService;

    public Portfolio toPortfolio(PortfolioDTO portfolioDTO) {
        User searchedUser = userRepository.findById(portfolioDTO.getUserId()).orElseThrow(() -> new UserNotFoundException(portfolioDTO.getUserId()));
        return new Portfolio(
                portfolioDTO.getId(),
                searchedUser,
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
