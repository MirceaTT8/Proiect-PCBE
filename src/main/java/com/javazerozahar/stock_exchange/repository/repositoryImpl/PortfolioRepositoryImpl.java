package com.javazerozahar.stock_exchange.repository.repositoryImpl;

import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PortfolioRepositoryImpl implements PortfolioRepository {

    private final Map<Long, Portfolio> portfolioStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);


    @Override
    public Portfolio save(Portfolio portfolio) {
        if (portfolio.getId() == null) {
            portfolio.setId(idCounter.incrementAndGet());
        }
        portfolioStore.put(portfolio.getId(), portfolio);
        return null;
    }

    @Override
    public <S extends Portfolio> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Portfolio> findById(Long id) {
        return Optional.ofNullable(portfolioStore.get(id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Portfolio> findAllByUserId(Long userId) {
        return portfolioStore.values().stream()
                .filter(portfolio -> portfolio.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Portfolio portfolio) {
        Optional.ofNullable(portfolio.getId())
                .filter(portfolioStore::containsKey)
                .ifPresentOrElse(
                        id -> portfolioStore.put(id, portfolio),
                        () -> { throw new NoSuchElementException("Portfolio not found for update."); }
                );
    }

    @Override
    public void deleteById(Long id) {
        portfolioStore.remove(id);
    }

    @Override
    public void delete(Portfolio entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Portfolio> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Portfolio> findAll() {
        return new ArrayList<>(portfolioStore.values());
    }

    @Override
    public List<Portfolio> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Optional<Portfolio> findByUserIdAndStock(Long userId, Stock stock) {
        return portfolioStore.values().stream()
                .filter(portfolio -> portfolio.getUserId().equals(userId) && portfolio.getStockId().equals(stock.getId()))
                .findFirst();
    }

    @Override
    public void reset() {
        idCounter.set(0);
        portfolioStore.clear();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Portfolio> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Portfolio> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Portfolio> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Portfolio getOne(Long aLong) {
        return null;
    }

    @Override
    public Portfolio getById(Long aLong) {
        return null;
    }

    @Override
    public Portfolio getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Portfolio> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Portfolio> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Portfolio> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Portfolio> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Portfolio> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Portfolio> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Portfolio, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Portfolio> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Portfolio> findAll(Pageable pageable) {
        return null;
    }
}
