package com.javazerozahar.stock_exchange.rabbit.general;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConfig {
//
//    private static final String QUEUE_NAME = "queue-names";
//    private static final String EXCHANGE_NAME = "exchange-name";
//    private static final String ROUTING_KEY = "routing-key";

    private static final String QUEUE_NAME = "order-queue";
    private static final String EXCHANGE_NAME = "exchange-queue";
    private static final String ROUTING_KEY = "routing-key";

    private final ConnectionFactory factory;

    public RabbitMQConfig() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");    // RabbitMQ host
        factory.setPort(5672);           // RabbitMQ port
        factory.setUsername("guest");    // RabbitMQ username
        factory.setPassword("guest");    // RabbitMQ password
    }

    public void setupQueueExchangeBinding() throws IOException, TimeoutException {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);

            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            System.out.println("Queue, exchange, and binding setup completed.");
        }
    }
}
