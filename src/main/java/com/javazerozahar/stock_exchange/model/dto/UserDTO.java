package com.javazerozahar.stock_exchange.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private List<PortfolioDTO> portfolioDTOs;
}
