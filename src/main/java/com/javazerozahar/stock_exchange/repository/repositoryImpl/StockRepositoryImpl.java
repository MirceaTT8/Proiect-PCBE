package com.javazerozahar.stock_exchange.repository.repositoryImpl;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class StockRepositoryImpl implements StockRepository {
    private final Map<Long, Stock> stocks = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public Stock save(Stock stock) {
        stock.setId(currentId.getAndIncrement());
        stocks.put(stock.getId(), stock);
        return null;
    }

    @Override
    public <S extends Stock> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Stock> findById(Long id) {
        return Optional.ofNullable(stocks.get(id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Stock> findAll() {
        return new ArrayList<>(stocks.values());
    }

    @Override
    public List<Stock> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
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
    public void delete(Stock entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Stock> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void reset() {
        stocks.clear();
        currentId.set(1);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Stock> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Stock> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Stock> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Stock getOne(Long aLong) {
        return null;
    }

    @Override
    public Stock getById(Long aLong) {
        return null;
    }

    @Override
    public Stock getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Stock> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Stock> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Stock> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Stock> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Stock> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Stock> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Stock, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Stock> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Stock> findAll(Pageable pageable) {
        return null;
    }
}