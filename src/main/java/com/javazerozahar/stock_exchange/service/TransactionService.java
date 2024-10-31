package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.exceptions.StockNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import com.javazerozahar.stock_exchange.model.entity.Transaction;
import com.javazerozahar.stock_exchange.repository.StockHistoryRepository;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import com.javazerozahar.stock_exchange.repository.TransactionRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockHistoryRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.TransactionRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.CurrencyConverter;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
public class TransactionService {

    private final PortfolioService portfolioService;
    private final TransactionRepository transactionRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final CurrencyConverter currencyConverter;
    private final StockRepository stockRepository;

    public TransactionService() {
        this.portfolioService = SingletonFactory.getInstance(PortfolioService.class);
        this.transactionRepository = SingletonFactory.getInstance(TransactionRepositoryImpl.class);
        this.stockHistoryRepository = SingletonFactory.getInstance(StockHistoryRepositoryImpl.class);
        this.currencyConverter = SingletonFactory.getInstance(CurrencyConverter.class);
        this.stockRepository = SingletonFactory.getInstance(StockRepositoryImpl.class);
    }

    public void createTransaction(Order order, Order matchingOrder, double matchedQuantity) {
        double convertedMatchedQuantity = matchingOrder.getPrice() * currencyConverter.convert(order.getPrice(), matchingOrder.getPrice(), matchedQuantity);

        if (order.getOrderType().equals(OrderType.BUY)) {
            portfolioService.updatePortfolio(matchingOrder, convertedMatchedQuantity);
            portfolioService.updatePortfolio(order, matchedQuantity);
        } else {
            portfolioService.updatePortfolio(matchingOrder, matchedQuantity);
            portfolioService.updatePortfolio(order, convertedMatchedQuantity);
        }

        double transactionedQuantity;
        Long stockId;
        Long sellerId, buyerId;

        if (order.getOrderType().equals(OrderType.BUY)) {
            transactionedQuantity = matchedQuantity;
            stockId = order.getBoughtStock().getId();
            sellerId = order.getUserId();
            buyerId = order.getUserId();
        } else {
            transactionedQuantity = convertedMatchedQuantity;
            stockId = matchingOrder.getBoughtStock().getId();
            sellerId = matchingOrder.getUserId();
            buyerId = matchingOrder.getUserId();
        }

        Transaction transaction = new Transaction();
        transaction.setPrice(order.getPrice());
        transaction.setQuantity(transactionedQuantity);
        transaction.setStockId(stockId);
        transaction.setTimestamp(System.currentTimeMillis());
        transaction.setSellerId(sellerId);
        transaction.setBuyerId(buyerId);

        transactionRepository.save(transaction);

        Optional<Stock> stock = stockRepository.findById(stockId);

        if (stock.isPresent()) {
            Stock currentStock = stock.get();
            currentStock.setPrice(order.getPrice());
            stockRepository.update(currentStock);

            StockHistory stockHistory = new StockHistory();
            stockHistory.setPrice(order.getPrice());
            stockHistory.setStockId(stockId);
            stockHistory.setTimestamp(System.currentTimeMillis());
            stockHistoryRepository.save(stockHistory);
        } else {
            throw new StockNotFoundException(stockId);
        }


        log.info("Transaction {}", transaction);
    }


}
