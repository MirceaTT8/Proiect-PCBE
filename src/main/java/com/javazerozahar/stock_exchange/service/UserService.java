package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.converters.UserConverter;
import com.javazerozahar.stock_exchange.exceptions.UserNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.UserDTO;
import com.javazerozahar.stock_exchange.model.entity.User;
import com.javazerozahar.stock_exchange.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional(readOnly = true)
    public UserDTO getUserDTO(Long userId) {
        return userConverter.toUserDTO(getUser(userId));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userConverter::toUserDTO)
                .toList();
    }

    @Transactional
    public UserDTO addUser(UserDTO userDTO) {
        return userConverter.toUserDTO(userRepository.save(userConverter.toUser(userDTO)));
    }

    @Transactional
    public User updateUser(UserDTO userDTO) {
        // TODO Patch mapping
        return userRepository.save(userConverter.toUser(userDTO));
    }
}
