package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.model.entity.Account;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Transaction;
import com.javazerozahar.stock_exchange.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

public class TransactionVerifier {
    public static boolean isValid(Transaction transaction, AccountRepository accountRepo){
        Optional<Account> buyer = accountRepo.findById(transaction.getBuyerId());
        Optional<Account> seller = accountRepo.findById(transaction.getSellerId());
        if (buyer.isEmpty() || seller.isEmpty()) return false;
        if (buyer.get().getBalance() < transaction.getPrice()) return false;
        List<Portfolio> sellerPortfolio = seller.get().getPortfolio();
        Optional<Portfolio> neededPortfolio = sellerPortfolio.stream()
                .filter(p -> p.getStockId().equals(transaction.getStockId()))
                .findFirst();
        if (neededPortfolio.isEmpty()) return false;
        if (transaction.getQuantity() > neededPortfolio.get().getQuantity()) return false;
        return true;
    }
}
