package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.UserDTO;
import com.javazerozahar.stock_exchange.model.entity.User;

public class UserConverter {
    public User toUser(UserDTO userDTO) {
        return new User(userDTO.getId());
    }
    public UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId());
    }
}
