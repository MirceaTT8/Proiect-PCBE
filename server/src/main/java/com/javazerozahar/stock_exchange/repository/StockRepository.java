package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Stock;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @NonNull
    @Query("SELECT s FROM Stock s WHERE s.id = :stockId AND s.isActive = true")
    Optional<Stock> findById(@NonNull Long stockId);

    @NonNull
    @Query("SELECT s FROM Stock s WHERE s.isActive = true")
    List<Stock> findAll();
}
