package com.javazerozahar.stock_exchange.rabbit.order;

import com.google.gson.Gson;
import com.javazerozahar.stock_exchange.converters.OrderConverter;
import com.javazerozahar.stock_exchange.model.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderPlacerProducer {

    @Value("${rabbitmq.queue.order}")
    private String queueName;

    private final OrderConverter orderConverter;
    private final RabbitTemplate rabbitTemplate;

    public void sendOrder(Order order) {
        try {
            String message = new Gson().toJson(orderConverter.toOrderDTO(order));
            rabbitTemplate.convertAndSend(queueName, message);

            log.info("Order sent to queue: {}",  message);

        } catch (Exception e) {
            log.error("Error sending order to queue", e);
        }
    }
}
