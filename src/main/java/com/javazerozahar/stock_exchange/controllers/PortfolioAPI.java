package com.javazerozahar.stock_exchange.controllers;

import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/portfolios")
@CrossOrigin(origins = "http://localhost:8080")
public class PortfolioAPI {
    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/")
    @ResponseBody
    public List<Portfolio> getAllPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    @GetMapping("/by-user/{userId}")
    public List<Portfolio> getAllPortfoliosByUser(@PathVariable("userId") Long userId) { return portfolioService.getPortfoliosByUser(userId); }

    @GetMapping("/by-user-stock/{userId}")
    public Portfolio getAllCoursesByMaxStudents(@PathVariable("userId") Long userId, @RequestBody Stock stock) { return portfolioService.getPortfolioByUserIdAndStock(userId, stock); }

    @PostMapping("/update/{quantity}")
    public Portfolio updatePortfolio(@RequestBody Order order, @PathVariable("quantity") double quantity) {
        return portfolioService.updatePortfolio(order, quantity);
    }
}
