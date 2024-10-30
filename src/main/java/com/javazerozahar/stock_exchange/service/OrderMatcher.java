package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.OrderRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.CurrencyConverter;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

@Log4j2
public class OrderMatcher {

    private final OrderRepository orderRepository;
    private final TransactionService transactionService;
    private final CurrencyConverter currencyConverter;

    public OrderMatcher() {
        this.orderRepository = SingletonFactory.getInstance(OrderRepositoryImpl.class);
        this.transactionService = SingletonFactory.getInstance(TransactionService.class);
        this.currencyConverter = SingletonFactory.getInstance(CurrencyConverter.class);
    }

    /**
     * Best price strategy <br>
     * For a sell order, the best price is the lowest buy price for which offers with enough stock units exist.
     * For a buy order, the best price is the highest sell price for which offers with enough stock units exist.
     * The orders are attended to in the order of submission and can be partially fulfilled.
     *
     * @param order
     */

    public Order matchOrder(Order order) {

        TreeMap<Double, PriorityQueue<Order>> orders = order.getOrderType().equals(OrderType.BUY) ?
                getSellOrdersForStock(order.getBoughtStock()) :
                getBuyOrdersForStock(order.getSoldStock());

        Map.Entry<Double, PriorityQueue<Order>> matchingOrders;


        if (order.getOrderType().equals(OrderType.BUY)) {
            matchingOrders = orders.floorEntry(order.getPrice());
        } else {
            matchingOrders = orders.ceilingEntry(order.getPrice());
        }

        if (matchingOrders != null) {

            log.info("MATCH {} matched \n{}", order, matchingOrders);

            PriorityQueue<Order> matchingQueue = matchingOrders.getValue();

            while (!matchingQueue.isEmpty()) {
                Order matchingOrder = matchingQueue.poll();

                if (order.getOrderType().equals(OrderType.BUY) && matchingOrder.getPrice() <= order.getPrice() ||
                        order.getOrderType().equals(OrderType.SELL) && matchingOrder.getPrice() >= order.getPrice()) {

                    double matchedQuantity = Math.min(order.getQuantity(), order.getOrderType().equals(OrderType.SELL) ?
                            matchingOrder.getQuantity() / matchingOrder.getSoldStock().getPrice() :
                            matchingOrder.getQuantity() / matchingOrder.getBoughtStock().getPrice());

                    order.setQuantity(order.getQuantity() - matchedQuantity);
                    matchingOrder.setQuantity(
                            matchingOrder.getQuantity() - (currencyConverter.convert(order.getPrice(), matchingOrder.getPrice(), matchedQuantity)));

                    if (matchingOrder.getQuantity() == 0) {
                        orderRepository.remove(matchingOrder);
                    }

                    transactionService.createTransaction(order, matchingOrder, matchedQuantity);

                    if (order.getQuantity() == 0) {
                        orderRepository.remove(order);
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return order;
    }



    private TreeMap<Double, PriorityQueue<Order>> getBuyOrdersForStock(Stock stock) {
        TreeMap<Double, PriorityQueue<Order>> orders = new TreeMap<>();
        orderRepository.findByBoughtStock(stock)
                .forEach(order ->
                    orders.computeIfAbsent(
                            order.getPrice(),
                            _ -> new PriorityQueue<>(Comparator.comparingLong(Order::getTimestamp))).add(order)
                );
        return orders;
    }

    private TreeMap<Double, PriorityQueue<Order>> getSellOrdersForStock(Stock stock) {
        TreeMap<Double, PriorityQueue<Order>> orders = new TreeMap<>();
        orderRepository.findBySoldStock(stock)
                .forEach(order ->
                        orders.computeIfAbsent(
                                order.getPrice(),
                                _ -> new PriorityQueue<>(Comparator.comparingLong(Order::getTimestamp))).add(order)
                );
        return orders;
    }

}
