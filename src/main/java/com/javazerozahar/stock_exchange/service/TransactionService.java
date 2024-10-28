package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Transaction;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import com.javazerozahar.stock_exchange.repository.TransactionRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.PortfolioRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.TransactionRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.CurrencyConverter;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

public class TransactionService {

    private final PortfolioRepository portfolioRepository;
    private final CurrencyConverter currencyConverter;
    private final TransactionRepository transactionRepository;

    public TransactionService() {
        this.portfolioRepository = SingletonFactory.getInstance(PortfolioRepositoryImpl.class);
        this.transactionRepository = SingletonFactory.getInstance(TransactionRepositoryImpl.class);

        this.currencyConverter = SingletonFactory.getInstance(CurrencyConverter.class);
    }

    public void createTransaction(Order order, Order matchingOrder, double matchedQuantity) {
        double convertedMatchedQuantity = currencyConverter.convert(order.getPrice(), matchingOrder.getPrice(), matchedQuantity);

        updatePorfolio(matchingOrder, convertedMatchedQuantity);
        updatePorfolio(order, matchedQuantity);

        double transactionedQuantity;
        Long stockId;
        Long sellerId, buyerId;

        if (order.getOrderType().equals(OrderType.BUY)) {
            transactionedQuantity = matchedQuantity;
            stockId = order.getBoughtStock().getId();
            sellerId = order.getUserId();
            buyerId = matchingOrder.getUserId();
        } else {
            transactionedQuantity = convertedMatchedQuantity;
            stockId = matchingOrder.getBoughtStock().getId();
            sellerId = matchingOrder.getUserId();
            buyerId = order.getUserId();
        }

        Transaction transaction = new Transaction();
        transaction.setPrice(order.getPrice());
        transaction.setQuantity(transactionedQuantity);
        transaction.setStockId(stockId);
        transaction.setTimestamp(System.currentTimeMillis());
        transaction.setSellerId(sellerId);
        transaction.setBuyerId(buyerId);

        transactionRepository.save(transaction);
    }

    private void updatePorfolio(Order order, double quantity) {
        Portfolio portfolio = portfolioRepository.findByUserIdAndStock(order.getUserId(), order.getBoughtStock());
        portfolio.setQuantity(portfolio.getQuantity() + quantity);
        portfolioRepository.save(portfolio);
    }
}
