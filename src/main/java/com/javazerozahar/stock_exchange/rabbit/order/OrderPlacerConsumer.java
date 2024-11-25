package com.javazerozahar.stock_exchange.rabbit.order;

import com.google.gson.Gson;
import com.javazerozahar.stock_exchange.converters.OrderConverter;
import com.javazerozahar.stock_exchange.exceptions.OrderNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.rabbit.general.MessageTracker;
import com.javazerozahar.stock_exchange.service.OrderMatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderPlacerConsumer {

    @Value("${rabbitmq.queue.order}")
    private String queueName;

    private final OrderMatcher orderMatcher;
    private final OrderConverter orderConverter;
    private final MessageTracker messageTracker;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "${rabbitmq.queue.order}", durable = "true"),
            exchange = @Exchange(name = "order-exchange"),
            key = "order-routing-key"
    ))
    public void receiveOrder(String message) {
        Order order = orderConverter.toOrder(new Gson().fromJson(message, OrderDTO.class));

        log.info("Received order from queue: {}", order);

        processOrder(order);
    }

    private void processOrder(Order order) {
        try {
            orderMatcher.matchOrder(order);

            if (log.isInfoEnabled()) {
                log.info("Processed order for user: {}", order.getUser().getId());
            }
        } catch (OrderNotFoundException e) {
            log.info("Order {} not found or was already matched", order.getOrderId());
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Error processing order for user: {}", order.getUser().getId(), e);
            }
        } finally {
            messageTracker.decrement(queueName);
        }
    }

}
