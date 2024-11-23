package com.javazerozahar.stock_exchange.rabbit.transaction;

import com.google.gson.Gson;
import com.javazerozahar.stock_exchange.converters.OrderConverter;
import com.javazerozahar.stock_exchange.service.TransactionService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionPlacerConsumer {

    private static final String QUEUE_NAME = "transaction_queue";
    private final ConnectionFactory connectionFactory;
    private final TransactionService transactionService;
    private final OrderConverter orderConverter;

    public void startListening() {
        try (Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            DeliverCallback deliverCallback = (_, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                processTransaction(new Gson().fromJson(message, TransactionRabbitMqDTO.class));
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, _ -> {
            });

            while (channel.isOpen()) {
                Thread.sleep(100);
            }

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processTransaction(TransactionRabbitMqDTO transaction) {

        try {
            transactionService.createTransaction(
                    orderConverter.toOrder(transaction.getOrder()),
                    orderConverter.toOrder(transaction.getMatchedOrder()),
                    transaction.getMatchedQuantity()
            );

            if (log.isInfoEnabled()) {
                log.info("Processed transaction for orders: {} {}",
                        transaction.getOrder(), transaction.getMatchedOrder());
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Unable to process transaction for orders {} {} ",
                        transaction.getOrder(), transaction.getMatchedOrder(), e);
            }
        }
    }

}
