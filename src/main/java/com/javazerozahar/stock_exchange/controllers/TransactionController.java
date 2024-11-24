package com.javazerozahar.stock_exchange.controllers;

import com.javazerozahar.stock_exchange.model.dto.TransactionDTO;
import com.javazerozahar.stock_exchange.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;

    @GetMapping
    public List<TransactionDTO> getAllTransactions(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long stockId
    ) {
        return transactionService.getAllTransactions(userId, stockId);
    }

    @GetMapping("/{transactionId}")
    public TransactionDTO getTransaction(@PathVariable("transactionId") Long transactionId) {
        return transactionService.getTransaction(transactionId);
    }

}
