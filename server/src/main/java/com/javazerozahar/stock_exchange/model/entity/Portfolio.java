package com.javazerozahar.stock_exchange.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "portfolios")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock", nullable = false)
    private Stock stock;

    @Column(nullable = false)
    private Double quantity;

    @Override
    public String toString() {
        return "Portfolio{" +
                "id=" + id +
//                ", userId=" + (user != null ? user.getId() : "null") +
                ", stock=" + stock +
                ", quantity=" + quantity +
                '}';
    }
}