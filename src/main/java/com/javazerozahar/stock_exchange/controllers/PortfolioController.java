package com.javazerozahar.stock_exchange.controllers;

import com.javazerozahar.stock_exchange.model.dto.PortfolioDTO;
import com.javazerozahar.stock_exchange.service.PortfolioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/portfolios")
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
public class PortfolioController {

    private PortfolioService portfolioService;

    @GetMapping
    public List<PortfolioDTO> getPortfolios(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long stockId
    ) {
        return portfolioService.getPortfolios(userId, stockId);
    }

}
