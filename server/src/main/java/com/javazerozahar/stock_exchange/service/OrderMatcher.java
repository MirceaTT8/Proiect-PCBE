package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.converters.OrderConverter;
import com.javazerozahar.stock_exchange.exceptions.OrderNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.rabbit.transaction.TransactionPlacerProducer;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.utils.CurrencyConverter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Log4j2
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class OrderMatcher {

    private final OrderRepository orderRepository;
    private final CurrencyConverter currencyConverter;
    private final OrderConverter orderConverter;
    private final TransactionPlacerProducer transactionPlacerProducer;
    private final TransactionService transactionService;
    private final PortfolioService portfolioService;
    private final OrderService orderService;

    /**
     * Best price strategy <br>
     * For a sell order, the best price is the lowest buy price for which offers with enough stock units exist.
     * For a buy order, the best price is the highest sell price for which offers with enough stock units exist.
     * The orders are attended to in the order of submission and can be partially fulfilled.
     *
     * @param order
     */
    @Transactional
    public Order matchOrder(Order order) {

        // Lock the current order and portfolio
        order = orderRepository.findByIdWithLock(order.getOrderId()).orElseThrow(OrderNotFoundException::new);
        portfolioService.getPortfolioByUserIdAndStockWithLock(order.getUser().getId(), order.getBoughtStock());


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

                    double matchedQuantity = Math.min(order.getQuantity(),
                            matchingOrder.getQuantity() );

                    log.info("Matched quantity a ajuns la {} /n", matchedQuantity);

                    order.setQuantity(order.getQuantity() - matchedQuantity);
                    matchingOrder.setQuantity(matchingOrder.getQuantity() - matchedQuantity);

                    log.info("Cantitatea a ajuns la {} /n", matchingOrder.getQuantity());
                    if (matchingOrder.getQuantity() == 0) {
                        log.info("Cantitatea a ajuns la 0 pentru matching order/n");
                        orderRepository.delete(matchingOrder);
                    }

                    transactionPlacerProducer.sendTransaction(order, matchingOrder, matchedQuantity);

                    log.info("Cantitatea a ajuns la {} /n", order.getQuantity());
                    if (order.getQuantity() == 0) {
                        log.info("Cantitatea a ajuns la 0 pentru order/n");
                        orderRepository.delete(order);
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
        orderRepository.findByBoughtStockIdWithLock(stock.getId())
                .forEach(order ->
                        orders.computeIfAbsent(
                                order.getPrice(),
                                _ -> new PriorityQueue<>(Comparator.comparingLong(Order::getTimestamp))).add(order)
                );
        return orders;
    }

    private TreeMap<Double, PriorityQueue<Order>> getSellOrdersForStock(Stock stock) {
        TreeMap<Double, PriorityQueue<Order>> orders = new TreeMap<>();
        orderRepository.findBySoldStockIdWithLock(stock.getId())
                .forEach(order ->
                        orders.computeIfAbsent(
                                order.getPrice(),
                                _ -> new PriorityQueue<>(Comparator.comparingLong(Order::getTimestamp))).add(order)
                );
        return orders;
    }

}
