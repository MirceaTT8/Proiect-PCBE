package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends Resettable, JpaRepository<Transaction, Long> {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(Long id);

    List<Transaction> findAll();

    List<Transaction> findAllByStockId(Long stockId);
    List<Transaction> findAllByUserId(Long userId);

    void update(Transaction transaction);

    void deleteById(Long id);
}

