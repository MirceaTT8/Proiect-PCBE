package com.javazerozahar.stock_exchange.converters;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.service.StockService;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

public class OrderConverter {

    private static StockService stockService = new StockService();

    public OrderConverter() {
        stockService = SingletonFactory.getInstance(StockService.class);
    }
    /**
     * Converts an OrderDTO to an Order entity.
     *
     * @param orderDTO The OrderDTO to convert.
     * @return The corresponding Order entity.
     */
    public static Order toOrder(OrderDTO orderDTO) {
        // Retrieve the sold and bought stocks based on their IDs
        Stock soldStock = stockService.getStock(orderDTO.getSoldStockId());
        Stock boughtStock = stockService.getStock(orderDTO.getBoughtStockId());

        // Create and return the Order entity
        return new Order(
                orderDTO.getOrderId(),
                orderDTO.getUserId(),
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
                order.getUserId(),
                order.getPrice(),
                order.getSoldStock().getId(),
                order.getBoughtStock().getId(),
                order.getQuantity(),
                order.getOrderType(),
                order.getTimestamp()
        );
    }
}
