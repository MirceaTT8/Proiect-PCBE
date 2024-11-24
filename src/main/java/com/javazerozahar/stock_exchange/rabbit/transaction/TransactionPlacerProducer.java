package com.javazerozahar.stock_exchange.rabbit.transaction;

import com.google.gson.Gson;
import com.javazerozahar.stock_exchange.converters.OrderConverter;
import com.javazerozahar.stock_exchange.model.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionPlacerProducer {

    @Value("${rabbitmq.queue.transaction}")
    private String queueName;

    private final RabbitTemplate rabbitTemplate;
    private final OrderConverter orderConverter;

    @Transactional
    public void sendTransaction(Order order, Order matchingOrder, double matchedQuantity) {

        try {
            String message = new Gson().toJson(new TransactionRabbitMqDTO(
                    orderConverter.toOrderDTO(order),
                    orderConverter.toOrderDTO(matchingOrder),
                    matchedQuantity
            ));

            rabbitTemplate.convertAndSend(queueName, message);

            log.info("Transaction sent to queue: {}", message);

        } catch (Exception e) {
            log.error("Error sending transaction to queue", e);
        }
    }

}