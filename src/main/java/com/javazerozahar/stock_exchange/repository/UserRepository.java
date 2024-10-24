package com.javazerozahar.stock_exchange.repository;

import com.javazerozahar.stock_exchange.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void update(User user);
    void deleteById(Long id);
}

