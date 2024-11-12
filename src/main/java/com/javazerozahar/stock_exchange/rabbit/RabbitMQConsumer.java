package com.javazerozahar.stock_exchange.rabbit;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQConsumer {

    private static final String QUEUE_NAME = "queue-names";
    private final ConnectionFactory factory;

    public RabbitMQConsumer() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
    }

    public void receiveMessages() {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            System.out.println("Waiting for messages...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("Received message from RabbitMQConsumerGeneral: " + message);
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            RabbitMQConfig config = new RabbitMQConfig();
            config.setupQueueExchangeBinding();

            RabbitMQConsumer consumer = new RabbitMQConsumer();
            consumer.receiveMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}