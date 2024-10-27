package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.AccountDTO;
import com.javazerozahar.stock_exchange.model.entity.Account;

public class AccountConverter {
    public Account toAccount(AccountDTO accountDTO) {
        return new Account(accountDTO.getId(), accountDTO.getUserId(), accountDTO.getBalance(), accountDTO.getPortfolio());
    }
    public AccountDTO toAccountDTO(Account account) {
        return new AccountDTO(account.getId(), account.getUserId(), account.getBalance(), account.getPortfolio());
    }
}
