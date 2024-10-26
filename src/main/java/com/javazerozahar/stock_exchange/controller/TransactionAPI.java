package com.javazerozahar.stock_exchange.controller;

import com.javazerozahar.stock_exchange.model.entity.Transaction;
import com.javazerozahar.stock_exchange.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "http://localhost:4200")

public class TransactionAPI {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/")
    public List<Transaction> findAllTransactions() { return transactionRepository.findAll(); }

    @GetMapping("/get/{id}")
    public Optional<Transaction> findTransactionById(@PathVariable("id") Long id) { return transactionRepository.findById(id); }

    @GetMapping("/get/stock/{id}")
    public List<Transaction> findAllTransactionsByStockId(@PathVariable("id") Long id) { return transactionRepository.findAllByStockId(id); }

    @GetMapping("/get/user/{id}")
    public List<Transaction> findAllTransactionsByUserId(@PathVariable("id") Long id) { return transactionRepository.findAllByUserId(id); }

    @PostMapping("/update")
    public void updateTransaction(@RequestBody Transaction transaction) { transactionRepository.update(transaction); }

    @DeleteMapping("/delete/{id}")
    public void deleteTransaction(@PathVariable("id") Long id) { transactionRepository.deleteById(id); }
}
