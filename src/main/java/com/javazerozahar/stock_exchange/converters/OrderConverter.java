package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.model.entity.User;
import com.javazerozahar.stock_exchange.service.StockService;
import com.javazerozahar.stock_exchange.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderConverter {

    private final StockService stockService;
    private final UserService userService;

    /**
     * Converts an OrderDTO to an Order entity.
     *
     * @param orderDTO The OrderDTO to convert.
     * @return The corresponding Order entity.
     */
    public Order toOrder(OrderDTO orderDTO) {
        // Retrieve the sold and bought stocks based on their IDs
        Stock soldStock = stockService.getStock(orderDTO.getSoldStockId());
        Stock boughtStock = stockService.getStock(orderDTO.getBoughtStockId());
        User user = userService.getUser(orderDTO.getUserId());

        // Create and return the Order entity
        return new Order(
                orderDTO.getOrderId(),
                user,
                orderDTO.getPrice(),
                soldStock,
                boughtStock,
                orderDTO.getQuantity(),
                orderDTO.getOrderType(),
                orderDTO.getTimestamp()
        );
    }

    /**
     * Converts an Order entity to an OrderDTO.
     *
     * @param order The Order entity to convert.
     * @return The corresponding OrderDTO.
     */
    public static OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
                order.getOrderId(),
                order.getUser().getId(),
                order.getPrice(),
                order.getSoldStock().getId(),
                order.getBoughtStock().getId(),
                order.getQuantity(),
                order.getOrderType(),
                order.getTimestamp()
        );
    }
}
