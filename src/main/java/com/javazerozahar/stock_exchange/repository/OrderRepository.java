package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBoughtStock(Stock stock);

    List<Order> findBySoldStock(Stock stock);

    @Query("SELECT o FROM Order o WHERE o.boughtStock.id = :stockId OR o.soldStock.id = :stockId")
    List<Order> findAllByStockId(Long stockId);

    List<Order> findByUserId(Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.boughtStock.id = :stockId OR o.soldStock.id = :stockId)")
    List<Order> findByUserIdStockId(Long userId, Long stockId);


    @Query("SELECT o FROM Order o WHERE o.boughtStock.id = :stockId")
    List<Order> findBuyByStockId(Long stockId);

    @Query("SELECT o FROM Order o WHERE o.soldStock.id = :stockId")
    List<Order> findSellByStockId(Long stockId);
}
