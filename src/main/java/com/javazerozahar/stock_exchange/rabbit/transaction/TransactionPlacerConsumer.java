package com.javazerozahar.stock_exchange.rabbit.transaction;

import com.google.gson.Gson;
import com.javazerozahar.stock_exchange.converters.OrderConverter;
import com.javazerozahar.stock_exchange.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionPlacerConsumer {

    private final TransactionService transactionService;
    private final OrderConverter orderConverter;

    @RabbitListener(queues = "${rabbitmq.queue.transaction}")
    public void receiveTransaction(String message) {
        processTransaction(new Gson().fromJson(message, TransactionRabbitMqDTO.class));
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
