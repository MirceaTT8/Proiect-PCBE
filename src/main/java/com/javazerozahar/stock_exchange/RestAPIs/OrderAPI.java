package com.javazerozahar.stock_exchange.RestAPIs;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderAPI {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    @ResponseBody
    public List<Order> getAllOrders() { return orderService.getAllOrders(); }

    @GetMapping("/{orderId}")
    public Optional<Order> getOrder(@PathVariable("orderId") Long orderId) { return orderService.getOrder(orderId); }

    @GetMapping("/by-bought-stock/")
    public List<Order> getAllOrdersByBoughtStock(@RequestBody Stock stock) { return orderService.getOrdersByBoughtStock(stock); }

    @GetMapping("/by-sold-stock/")
    public List<Order> getAllOrdersBySoldStock(@RequestBody Stock stock) { return orderService.getOrdersBySoldStock(stock); }

    @PostMapping("/create/")
    public void createOrder(@RequestBody OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO,"create");
    }

    @PostMapping("/update/")
    public void updateOrder(@RequestBody OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO,"update");
    }

    @DeleteMapping("/delete/")
    public void deleteOrder(@RequestBody OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO,"delete");
    }
}
