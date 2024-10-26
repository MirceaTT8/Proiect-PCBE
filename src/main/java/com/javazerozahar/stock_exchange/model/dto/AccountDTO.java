package com.javazerozahar.stock_exchange.model.dto;

import com.javazerozahar.stock_exchange.model.entity.Portfolio;

import java.util.List;

public class AccountDTO {
    private Long id;
    private Long userId;
    private Double balance;
    private List<Portfolio> portfolio;

    public AccountDTO(Long id, Long userId, Double balance, List<Portfolio> portfolio) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.portfolio = portfolio;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getBalance() {
        return balance;
    }

    public List<Portfolio> getPortfolio() {
        return portfolio;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setPortfolio(List<Portfolio> portfolio) {
        this.portfolio = portfolio;
    }
}
