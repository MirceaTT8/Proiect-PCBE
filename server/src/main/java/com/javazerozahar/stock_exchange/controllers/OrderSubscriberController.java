package com.javazerozahar.stock_exchange.controllers;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class OrderSubscriberController {
    // For managing client connections
    private final Set<SseEmitter> emitters = Collections.synchronizedSet(new HashSet<>());

    @GetMapping(path = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        // Add emitter to the connected clients
        emitters.add(emitter);

        // Remove emitter when connection is completed/timed out
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        // Send initial data if needed
        try {
            emitter.send(SseEmitter.event()
                    .name("INIT")
                    .data("Connected!"));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    // Method to broadcast events to all clients
    public void broadcastEvent(String eventName, OrderDTO orderDTO) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(orderDTO));
            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        });

        // Remove dead emitters
        emitters.removeAll(deadEmitters);
    }

    // Example: Trigger event when database changes
    public void updateData(Order order) {
        // Process data/update database
        broadcastEvent("DATA_UPDATE", new OrderDTO(
                order.getOrderId(),
                order.getUser().getId(),
                order.getPrice(),
                order.getSoldStock().getId(),
                order.getBoughtStock().getId(),
                order.getQuantity(),
                order.getOrderType(),
                order.getTimestamp())
        );
    }
}
