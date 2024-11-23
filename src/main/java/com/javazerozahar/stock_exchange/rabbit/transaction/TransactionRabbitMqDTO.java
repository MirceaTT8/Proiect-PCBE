package com.javazerozahar.stock_exchange.rabbit.transaction;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionRabbitMqDTO {
    private OrderDTO order;
    private OrderDTO matchedOrder;
    private double matchedQuantity;
}
