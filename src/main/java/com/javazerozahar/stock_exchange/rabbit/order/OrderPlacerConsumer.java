package com.javazerozahar.stock_exchange.rabbit.order;

import com.google.gson.Gson;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.service.OrderMatcher;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Service
public class OrderPlacerConsumer {

    private static final String QUEUE_NAME = "order-queue";
    private final ConnectionFactory connectionFactory;

    private final OrderMatcher orderMatcher;

    public OrderPlacerConsumer(OrderMatcher orderMatcher) {
        this.orderMatcher = orderMatcher;
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
                processOrder(new Gson().fromJson(message, Order.class));
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});

            while (channel.isOpen()) {
                Thread.sleep(100);
            }

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processOrder(Order order) {
        try {
            orderMatcher.matchOrder(order);
            System.out.println("Processed order for user: " + order.getUser().getId());
        } catch (Exception e) {
            System.err.println("Failed to process order: " + e.getMessage());
        }
    }

}
