package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBoughtStock(Stock stock);

    List<Order> findBySoldStock(Stock stock);
}
