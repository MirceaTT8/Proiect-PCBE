package com.javazerozahar.stock_exchange.repository.repositoryImpl;

import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PortfolioRepositoryImpl implements PortfolioRepository {

    private final Map<Long, Portfolio> portfolioStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public void save(Portfolio portfolio) {
        if (portfolio.getId() == null) {
            portfolio.setId(idCounter.incrementAndGet());
        }
        portfolioStore.put(portfolio.getId(), portfolio);
    }

    @Override
    public Optional<Portfolio> findById(Long id) {
        return Optional.ofNullable(portfolioStore.get(id));
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
    public List<Portfolio> findAll() {
        return new ArrayList<>(portfolioStore.values());
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
}
