package com.javazerozahar.stock_exchange.controllers;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @GetMapping
    public List<OrderDTO> getAllOrders(
            @RequestParam(required = false) Long stockId,
            @RequestParam(required = false) String orderType
    ) {
        return orderService.getAllOrders(stockId, orderType);
    }

    @GetMapping("/{orderId}")
    public Optional<Order> getOrder(@PathVariable("orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrdersForUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String stockId
    ) {
        return orderService.getOrdersByUser(userId, stockId);
    }

    @PostMapping
    public void createOrder(@RequestBody OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO,"create");
    }

    @PatchMapping
    public void updateOrder(@RequestBody OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO,"update");
    }

    @DeleteMapping
    public void deleteOrder(@RequestBody OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO,"delete");
    }
}
