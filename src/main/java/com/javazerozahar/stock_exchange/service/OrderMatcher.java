package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.OrderRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.PortfolioRepositoryImpl;
import com.javazerozahar.stock_exchange.service.orderplacer.OrderPlacer;
import com.javazerozahar.stock_exchange.utils.CurrencyConverter;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class OrderMatcher {

    private final OrderRepository orderRepository;
    private final PortfolioRepository portfolioRepository;
    private final TransactionService transactionService;
    private final CurrencyConverter currencyConverter;

    private final OrderPlacer orderPlacer;

    public OrderMatcher() {
        this.orderRepository = SingletonFactory.getInstance(OrderRepositoryImpl.class);
        this.portfolioRepository = SingletonFactory.getInstance(PortfolioRepositoryImpl.class);
        this.orderPlacer = SingletonFactory.getInstance(OrderPlacer.class);
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

        TreeMap<Double, PriorityQueue<Order>> orders = getOrdersForStock(order.getBoughtStock());

        PriorityQueue<Order> matchingQueue;
        if (order.getOrderType().equals(OrderType.BUY)) {
            matchingQueue = orders.floorEntry(order.getPrice()).getValue();
        } else {
            matchingQueue = orders.ceilingEntry(order.getPrice()).getValue();
        }

        while (!matchingQueue.isEmpty()) {
            Order matchingOrder = matchingQueue.poll();

            if (order.getOrderType().equals(OrderType.BUY) && matchingOrder.getPrice() <= order.getPrice() ||
                    order.getOrderType().equals(OrderType.SELL) && matchingOrder.getPrice() >= order.getPrice()) {

                double matchedQuantity = Math.min(order.getQuantity(), matchingOrder.getQuantity() / matchingOrder.getPrice());

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

        return order;
    }



    private TreeMap<Double, PriorityQueue<Order>> getOrdersForStock(Stock stock) {
        TreeMap<Double, PriorityQueue<Order>> orders = new TreeMap<>();
        orderRepository.findByBoughtStock(stock)
                .forEach(order ->
                    orders.computeIfAbsent(
                            order.getPrice(),
                            _ -> new PriorityQueue<>(Comparator.comparingLong(Order::getTimestamp))).add(order)
                );
        return orders;
    }


}
