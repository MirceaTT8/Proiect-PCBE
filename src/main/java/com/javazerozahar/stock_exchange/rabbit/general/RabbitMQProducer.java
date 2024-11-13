package com.javazerozahar.stock_exchange.rabbit.general;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class RabbitMQProducer {

    private static final String EXCHANGE_NAME = "exchange-name";
    private static final String ROUTING_KEY = "routing-key";
    private final ConnectionFactory factory;

    public RabbitMQProducer() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");  // Set RabbitMQ host, default is localhost
        factory.setPort(5672);         // Set RabbitMQ port, default is 5672
        factory.setUsername("guest");  // Set RabbitMQ username
        factory.setPassword("guest");  // Set RabbitMQ password
    }

    public void sendMessage(String message) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);

            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
