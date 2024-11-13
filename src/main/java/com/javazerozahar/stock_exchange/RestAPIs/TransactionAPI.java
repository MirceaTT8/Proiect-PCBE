package com.javazerozahar.stock_exchange.RestAPIs;

import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.model.entity.Transaction;
import com.javazerozahar.stock_exchange.service.PortfolioService;
import com.javazerozahar.stock_exchange.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:8080")
public class TransactionAPI {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    @ResponseBody
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{transactionId}")
    public Optional<Transaction> getTransaction(@PathVariable("transactionId") Long transactionId) { return transactionService.getTransaction(transactionId); }

    @GetMapping("/by-user/{userId}")
    public List<Transaction> getAllTransactionsByUser(@PathVariable("userId") Long userId) { return transactionService.getAllTransactionsByUser(userId); }

    @GetMapping("/by-stock/{stockId}")
    public List<Transaction> getAllTransactionsWithStock(@PathVariable("stockId") Long stockId) { return transactionService.getAllTransactionsWithStock(stockId); }

    //Ar trebui sa se faca automat la adaugarea/creearea unui order
    /*
    @PostMapping("/create/{orderId}-{matchingOrderId}-{matchedQuantity}")
    public void createTransaction(@PathVariable("orderId") Long orderId, @PathVariable("matchingOrderId") Long matchingOrderId, @PathVariable("matchedQuantity") double matchedQuantity) {
        transactionService.createTransaction(orderId, matchingOrderId, matchedQuantity);
    }
     */
}
