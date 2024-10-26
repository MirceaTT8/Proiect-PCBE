package com.javazerozahar.stock_exchange.model.dto;

public class UserDTO {
    private Long id;

    public UserDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
