package com.javazerozahar.stock_exchange.model.dto;

import com.javazerozahar.stock_exchange.model.annotations.DtoId;
import com.javazerozahar.stock_exchange.model.annotations.Updatable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @DtoId
    private Long id;

    @Updatable
    private String lastName;

    @Updatable
    private String firstName;

    @Updatable
    private String email;

    @Updatable
    private String phoneNumber;

    private List<PortfolioDTO> portfolioDTOs = new ArrayList<>();
}
