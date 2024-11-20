package com.javazerozahar.stock_exchange.model.entity;

import com.javazerozahar.stock_exchange.model.dto.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sold_stock_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Stock soldStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bought_stock_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Stock boughtStock;

    @Column(nullable = false)
    private Double quantity;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(nullable = false)
    private Long timestamp;
}
