package com.javazerozahar.stock_exchange.rabbit.order;

import com.google.gson.Gson;
import com.javazerozahar.stock_exchange.converters.OrderConverter;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderPlacerProducer {
    private static final String QUEUE_NAME = "order-queue";

    private final ConnectionFactory connectionFactory;
    private final OrderConverter orderConverter;

    public void sendOrder(Order order) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            String message = new Gson().toJson(orderConverter.toOrderDTO(order));

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

            if (log.isInfoEnabled()) {
                log.info("Order sent to queue: " + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
