package com.javazerozahar.stock_exchange.rabbit.general;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageTracker {
    private final Map<String, Integer> pendingCounts = new ConcurrentHashMap<>();
    private final Map<String, CompletableFuture<Void>> completionFutures = new ConcurrentHashMap<>();

    public void increment(String queueName) {
        pendingCounts.merge(queueName, 1, Integer::sum);

        completionFutures.put(queueName, new CompletableFuture<>());
    }

    public void decrement(String queueName) {
        pendingCounts.computeIfPresent(queueName, (_, v) -> v > 1 ? v - 1 : null);

        if (pendingCounts.get(queueName) == null) {
            completionFutures.remove(queueName).complete(null);
        }
    }

    public void waitUntilQueueEmpty(String queueName) {
        CompletableFuture<Void> future = completionFutures.get(queueName);
        if (future != null) {
            future.join();
        }
    }
}