package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.TransactionDTO;
import com.javazerozahar.stock_exchange.model.entity.Transaction;
import com.javazerozahar.stock_exchange.service.StockService;
import com.javazerozahar.stock_exchange.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionConverter {

    private final UserService userService;
    private final StockService stockService;

    public Transaction toTransaction(TransactionDTO transactionDTO) {
        return new Transaction(
                transactionDTO.getId(),
                stockService.getStock(transactionDTO.getStockId()),
                userService.getUser(transactionDTO.getSellerId()),
                userService.getUser(transactionDTO.getBuyerId()),
                transactionDTO.getQuantity(),
                transactionDTO.getPrice(),
                transactionDTO.getTimestamp());
    }

    public TransactionDTO toTransactionDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getStock().getId(),
                transaction.getBuyer().getId(),
                transaction.getSeller().getId(),
                transaction.getQuantity(),
                transaction.getPrice(),
                transaction.getTimestamp());
    }
}
