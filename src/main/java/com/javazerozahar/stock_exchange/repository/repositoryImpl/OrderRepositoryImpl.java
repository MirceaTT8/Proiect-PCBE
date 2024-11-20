package com.javazerozahar.stock_exchange.repository.repositoryImpl;

import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {

    private final Map<Long, Order> orderStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    private final ConcurrentHashMap<Long, ReentrantLock> orderLocks = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        if (order.getOrderId() == null) {
            order.setOrderId(idCounter.incrementAndGet());
        }
        orderStore.put(order.getOrderId(), order);
        return order;
    }


    @Override
    public Optional<Order> findById(Long orderId) {
        return Optional.ofNullable(orderStore.get(orderId));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void remove(Order order) {
        if (order.getOrderId() != null) {
            orderStore.remove(order.getOrderId());
        }
    }

    @Override
    public List<Order> findByBoughtStock(Stock stock) {
        return orderStore.values().stream()
                .filter(order -> order.getBoughtStock().equals(stock))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findBySoldStock(Stock stock) {
        return orderStore.values().stream()
                .filter(order -> order.getSoldStock().equals(stock))
                .collect(Collectors.toList());
    }

    @Override
    public <S extends Order> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Order> findAll() {
        return orderStore.values().stream().toList();
    }

    @Override
    public List<Order> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Order entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Order> entities) {

    }

    @Override
    public void deleteAll() {

    }

    private Lock getOrderLock(Long orderId) {
        return orderLocks.computeIfAbsent(orderId, _ -> new ReentrantLock(true));
    }

    public void lockOrder(Long orderId) {
        Lock lock = getOrderLock(orderId);
        lock.lock();
    }

    public void unlockOrder(Long orderId) {
        Lock lock = getOrderLock(orderId);
        lock.unlock();
    }

    @Override
    public void reset() {
        orderStore.clear();
        this.idCounter.set(1);
        orderLocks.clear();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Order> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Order> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Order> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Order getOne(Long aLong) {
        return null;
    }

    @Override
    public Order getById(Long aLong) {
        return null;
    }

    @Override
    public Order getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Order> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Order> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Order> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Order> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Order> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Order> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Order, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Order> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return null;
    }
}

