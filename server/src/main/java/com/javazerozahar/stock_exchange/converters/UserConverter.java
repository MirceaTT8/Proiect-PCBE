package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.UserDTO;
import com.javazerozahar.stock_exchange.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserConverter {

    private final PortfolioConverter portfolioConverter;

    public User toUser(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getPortfolioDTOs().stream().map(portfolioConverter::toPortfolio).toList());
    }
    public UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getPortfolios().stream().map(portfolioConverter::toPortfolioDTO).toList());
    }
}
