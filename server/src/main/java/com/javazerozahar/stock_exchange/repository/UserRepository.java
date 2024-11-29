package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

