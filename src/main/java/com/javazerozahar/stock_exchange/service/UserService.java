package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.exceptions.UserNotFoundException;
import com.javazerozahar.stock_exchange.model.entity.User;
import com.javazerozahar.stock_exchange.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
