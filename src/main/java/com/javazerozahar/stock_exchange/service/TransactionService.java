package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Transaction;
import com.javazerozahar.stock_exchange.repository.TransactionRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.TransactionRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.CurrencyConverter;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TransactionService {

    private final PortfolioService portfolioService;
    private final CurrencyConverter currencyConverter;
    private final TransactionRepository transactionRepository;

    public TransactionService() {
        this.portfolioService = SingletonFactory.getInstance(PortfolioService.class);
        this.transactionRepository = SingletonFactory.getInstance(TransactionRepositoryImpl.class);

        this.currencyConverter = SingletonFactory.getInstance(CurrencyConverter.class);
    }

    public void createTransaction(Order order, Order matchingOrder, double matchedQuantity) {
        double convertedMatchedQuantity = currencyConverter.convert(order.getPrice(), matchingOrder.getPrice(), matchedQuantity);

        portfolioService.updatePorfolio(matchingOrder, convertedMatchedQuantity);
        portfolioService.updatePorfolio(order, matchedQuantity);

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

        log.info("Transaction {}", transaction);
    }


}
