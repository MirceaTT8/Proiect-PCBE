package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.RestAPIs.*;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.model.entity.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.javazerozahar.stock_exchange.repository")
public class SpringApp {
    public static void main(String[] args) {
        Initialize.start();
        PortfolioAPI portfolioAPI = new PortfolioAPI();
        StockAPI stockAPI = new StockAPI();
        StockHistoryAPI stockHistoryAPI = new StockHistoryAPI();
        TransactionAPI transactionAPI = new TransactionAPI();
        OrderAPI orderAPI = new OrderAPI();
        SpringApplication.run(SpringApp.class, args);
    }
}
