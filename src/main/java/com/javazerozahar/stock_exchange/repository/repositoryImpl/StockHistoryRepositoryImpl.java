package com.javazerozahar.stock_exchange.repository.repositoryImpl;

import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import com.javazerozahar.stock_exchange.repository.StockHistoryRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class StockHistoryRepositoryImpl implements StockHistoryRepository {
    private final Map<Long, List<StockHistory>> stockHistories = new ConcurrentHashMap<>();

    @Override
    public StockHistory save(StockHistory stockHistory) {
        stockHistories.computeIfAbsent(stockHistory.getStockId(), k -> new ArrayList<>()).add(stockHistory);
        return null;
    }

    @Override
    public List<StockHistory> findByStockId(Long stockId) {
        return stockHistories.getOrDefault(stockId, new ArrayList<>());
    }

    @Override
    public void reset() {
        stockHistories.clear();
    }

    @Override
    public <S extends StockHistory> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<StockHistory> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<StockHistory> findAll() {
        return null;
    }

    @Override
    public List<StockHistory> findAllById(Iterable<Long> longs) {
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
    public void delete(StockHistory entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends StockHistory> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends StockHistory> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends StockHistory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<StockHistory> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public StockHistory getOne(Long aLong) {
        return null;
    }

    @Override
    public StockHistory getById(Long aLong) {
        return null;
    }

    @Override
    public StockHistory getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends StockHistory> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends StockHistory> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends StockHistory> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends StockHistory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends StockHistory> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends StockHistory> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends StockHistory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<StockHistory> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<StockHistory> findAll(Pageable pageable) {
        return null;
    }
}