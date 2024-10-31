package com.javazerozahar.stock_exchange.repository.repositoryImpl;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.StockRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class StockRepositoryImpl implements StockRepository {
    private final Map<Long, Stock> stocks = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public void save(Stock stock) {
        stock.setId(currentId.getAndIncrement());
        stocks.put(stock.getId(), stock);
    }

    @Override
    public Optional<Stock> findById(Long id) {
        return Optional.ofNullable(stocks.get(id));
    }

    @Override
    public List<Stock> findAll() {
        return new ArrayList<>(stocks.values());
    }

    @Override
    public void update(Stock stock) {
        stocks.put(stock.getId(), stock);
    }

    @Override
    public void deleteById(Long id) {
        stocks.remove(id);
    }

    @Override
    public void reset() {
        stocks.clear();
        currentId.set(1);
    }
}