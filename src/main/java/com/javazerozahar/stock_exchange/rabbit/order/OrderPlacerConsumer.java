package com.javazerozahar.stock_exchange.rabbit.order;

import com.google.gson.Gson;
import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.dto.OrderMessageDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.service.orderplacer.OrderPlacer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class OrderPlacerConsumer {

    private static final String QUEUE_NAME = "order.queue";
    private final OrderPlacer orderPlacer;
    private final ConnectionFactory connectionFactory;

    public OrderPlacerConsumer(OrderPlacer orderPlacer) {
        this.orderPlacer = orderPlacer;
        this.connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
    }

    public void startListening() {
        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                OrderMessageDTO orderMessage = new Gson().fromJson(message, OrderMessageDTO.class);
                processOrder(orderMessage);
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private void processOrder(OrderMessageDTO orderMessage) {
        try {

            Order processedOrder = orderPlacer.placeOrder(orderMessage.getOrder(), orderMessage.getOrderPlacementStrategy());
            System.out.println("Processed order for user: " + processedOrder.getUserId());
        } catch (Exception e) {
            System.err.println("Failed to process order: " + e.getMessage());
        }
    }
}
