package com.javazerozahar.stock_exchange.rabbit.order;

import com.google.gson.Gson;
import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.dto.OrderMessageDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class OrderPlacerProducer {
    private static final String QUEUE_NAME = "order-queue";
    private final ConnectionFactory connectionFactory;

    public OrderPlacerProducer() {
        this.connectionFactory = new ConnectionFactory();
        connectionFactory.setPort(5672);
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
    }

    public void sendOrder(OrderDTO order, String orderPlacementStrategy) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            OrderMessageDTO orderMessage = new OrderMessageDTO(order, orderPlacementStrategy);

            String message = new Gson().toJson(orderMessage);

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Order sent to queue: " + message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
