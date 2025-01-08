package com.javazerozahar.stock_exchange.model.listeners;

import com.javazerozahar.stock_exchange.controllers.OrderSubscriberController;
import com.javazerozahar.stock_exchange.model.entity.Order;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderListener {

    private final OrderSubscriberController orderSubscriberController;

    public OrderListener(OrderSubscriberController orderSubscriberController) {
        this.orderSubscriberController = orderSubscriberController;
    }

    @PostPersist
    public void onPostPersist(Order order) {
        // Called after insert
        log.info("Created order {}", order.getOrderId());
        orderSubscriberController.updateData(order);
    }

    @PostUpdate
    public void onPostUpdate(Order order) {
        // Called after update
        log.info("Updated order {}", order.getOrderId());
        orderSubscriberController.updateData(order);
    }

    @PostRemove
    public void onPostRemove(Order order) {
        // Called after delete
        log.info("Deleted order {}", order.getOrderId());
        orderSubscriberController.updateData(order);
    }
}
