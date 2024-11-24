package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.Order;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Order o WHERE o.boughtStock.id = :stockId")
    List<Order> findByBoughtStockIdWithLock(Long stockId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Order o WHERE o.soldStock.id = :stockId")
    List<Order> findBySoldStockIdWithLock(Long stockId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Order o WHERE o.orderId = :id")
    Optional<Order> findByIdWithLock(Long id);

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
