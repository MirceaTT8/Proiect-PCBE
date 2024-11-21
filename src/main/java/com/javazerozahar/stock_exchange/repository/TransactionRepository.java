package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByStockId(Long stockId);

    @Query("SELECT t FROM Transaction t WHERE t.buyer.id = :userId OR t.seller.id = :userId")
    List<Transaction> findAllByUserId(Long userId);

}

