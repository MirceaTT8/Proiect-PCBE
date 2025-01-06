package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.converters.UserConverter;
import com.javazerozahar.stock_exchange.exceptions.InvalidCredentialsException;
import com.javazerozahar.stock_exchange.exceptions.UserNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.UserDTO;
import com.javazerozahar.stock_exchange.model.entity.User;
import com.javazerozahar.stock_exchange.repository.UserRepository;
import com.javazerozahar.stock_exchange.utils.Patcher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final Patcher patcher;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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
    public UserDTO updateUser(UserDTO userDTO) {
        UserDTO userToBeUpdated = getUserDTO(userDTO.getId());
        User user = userConverter.toUser(patcher.patch(userToBeUpdated, userDTO));
        userRepository.save(user);
        return userConverter.toUserDTO(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        String hashedPassword = encoder.encode(userDTO.getPassword());
        userDTO.setPassword(hashedPassword);
        return addUser(userDTO);
    }

    public void login(UserDTO userDTO) {
        Long userId = userDTO.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (encoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
    }
}
