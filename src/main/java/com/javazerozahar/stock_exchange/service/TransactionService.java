package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.converters.TransactionConverter;
import com.javazerozahar.stock_exchange.exceptions.TransactionNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.model.dto.TransactionDTO;
import com.javazerozahar.stock_exchange.model.entity.*;
import com.javazerozahar.stock_exchange.repository.StockHistoryRepository;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import com.javazerozahar.stock_exchange.repository.TransactionRepository;
import com.javazerozahar.stock_exchange.utils.CurrencyConverter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class TransactionService {

    private final PortfolioService portfolioService;
    private final TransactionRepository transactionRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final CurrencyConverter currencyConverter;
    private final StockRepository stockRepository;
    private final TransactionConverter transactionConverter;

    public void createTransaction(Order order, Order matchingOrder, double matchedQuantity) {
        double convertedMatchedQuantity = matchingOrder.getPrice() * currencyConverter.convert(order.getPrice(), matchingOrder.getPrice(), matchedQuantity);

        if (order.getOrderType().equals(OrderType.BUY)) {
            portfolioService.updatePortfolio(matchingOrder, convertedMatchedQuantity);
            portfolioService.updatePortfolio(order, matchedQuantity);
        } else {
            portfolioService.updatePortfolio(matchingOrder, matchedQuantity);
            portfolioService.updatePortfolio(order, convertedMatchedQuantity);
        }

        double transitionedQuantity;
        Stock stock;
        User seller, buyer;

        if (order.getOrderType().equals(OrderType.BUY)) {
            transitionedQuantity = matchedQuantity;
            stock = order.getBoughtStock();
            seller = matchingOrder.getUser();
            buyer = order.getUser();
        } else {
            transitionedQuantity = convertedMatchedQuantity;
            stock = matchingOrder.getBoughtStock();
            seller = matchingOrder.getUser();
            buyer = order.getUser();
        }

        Transaction transaction = new Transaction();
        transaction.setPrice(order.getPrice());
        transaction.setQuantity(transitionedQuantity);
        transaction.setStock(stock);
        transaction.setTimestamp(System.currentTimeMillis());
        transaction.setSeller(seller);
        transaction.setBuyer(buyer);

        transactionRepository.save(transaction);

        stock.setPrice(order.getPrice());
        stock = stockRepository.save(stock);

        StockHistory stockHistory = new StockHistory();
        stockHistory.setPrice(order.getPrice());
        stockHistory.setStock(stock);
        stockHistory.setTimestamp(System.currentTimeMillis());
        stockHistoryRepository.save(stockHistory);

        log.info("Transaction {}", transaction);
    }

    public TransactionDTO getTransaction(Long transactionId) {
        return transactionConverter.toTransactionDTO(
                transactionRepository.findById(transactionId)
                        .orElseThrow(() -> new TransactionNotFoundException(transactionId)));
    }

    public List<TransactionDTO> getAllTransactions(Long userId, Long stockId) {
        if (userId == null && stockId == null) {
            return transactionRepository.findAll().stream().map(transactionConverter::toTransactionDTO).toList();
        }

        if (userId != null && stockId == null) {
            return transactionRepository.findAllByUserId(userId).stream().map(transactionConverter::toTransactionDTO).toList();
        }

        if (userId == null) {
            return transactionRepository.findAllByStockId(stockId).stream().map(transactionConverter::toTransactionDTO).toList();
        }

        return transactionRepository.findAllByUserIdAndStockId(userId, stockId).stream().map(transactionConverter::toTransactionDTO).toList();
    }

}
