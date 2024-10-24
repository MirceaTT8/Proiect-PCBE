package com.javazerozahar.stock_exchange.repository.repositoryImpl;
import com.javazerozahar.stock_exchange.model.entity.Transaction;
import com.javazerozahar.stock_exchange.repository.TransactionRepository;

import java.util.*;

public class TransactionRepositoryImpl implements TransactionRepository {
    private final Map<Long, Transaction> transactions = new HashMap<>();
    private long currentId = 1;

    @Override
    public void save(Transaction transaction) {
        transaction.setId(currentId++);
        transactions.put(transaction.getId(), transaction);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return Optional.ofNullable(transactions.get(id));
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions.values());
    }

    @Override
    public List<Transaction> findAllByStockId(Long stockId) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions.values()) {
            if (transaction.getStockId().equals(stockId)) {
                result.add(transaction);
            }
        }
        return result;
    }

    @Override
    public List<Transaction> findAllByUserId(Long userId) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions.values()) {
            if (transaction.getBuyerId().equals(userId) || transaction.getSellerId().equals(userId)) {
                result.add(transaction);
            }
        }
        return result;
    }

    @Override
    public void update(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    @Override
    public void deleteById(Long id) {
        transactions.remove(id);
    }
}