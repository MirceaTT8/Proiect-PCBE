package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.TransactionDTO;
import com.javazerozahar.stock_exchange.model.entity.Transaction;

public class TransactionConverter {
    public Transaction toTransaction(TransactionDTO transactionDTO) {
        return new Transaction(transactionDTO.getId(), transactionDTO.getStockId(), transactionDTO.getSellerId(), transactionDTO.getBuyerId(), transactionDTO.getQuantity(), transactionDTO.getPrice(), transactionDTO.getTimestamp());
    }
    public TransactionDTO toTransactionDTO(Transaction transaction) {
        return new TransactionDTO(transaction.getId(), transaction.getStockId(), transaction.getSellerId(), transaction.getBuyerId(), transaction.getQuantity(), transaction.getPrice(), transaction.getTimestamp());
    }
}
