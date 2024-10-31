package com.javazerozahar.stock_exchange.repository.repositoryImpl;
import com.javazerozahar.stock_exchange.model.entity.Transaction;
import com.javazerozahar.stock_exchange.repository.TransactionRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionRepositoryImpl implements TransactionRepository {
    private final Map<Long, Transaction> transactions = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public void save(Transaction transaction) {
        transaction.setId(currentId.getAndIncrement());
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
        return this.transactions.values().stream()
                .filter(transaction -> transaction.getStockId().equals(stockId))
                .toList();
    }

    @Override
    public List<Transaction> findAllByUserId(Long userId) {
        return this.transactions.values().stream()
                .filter(transaction -> transaction.getBuyerId().equals(userId)
                        || transaction.getSellerId().equals(userId))
                .toList();
    }

    @Override
    public void update(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    @Override
    public void deleteById(Long id) {
        transactions.remove(id);
    }

    @Override
    public void reset() {
        transactions.clear();
        currentId.set(1);
    }
}