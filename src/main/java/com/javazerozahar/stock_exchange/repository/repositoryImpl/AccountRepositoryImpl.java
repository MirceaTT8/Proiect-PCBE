package com.javazerozahar.stock_exchange.repository.repositoryImpl;

import com.javazerozahar.stock_exchange.model.entity.Account;
import com.javazerozahar.stock_exchange.repository.AccountRepository;

import java.util.*;

public class AccountRepositoryImpl implements AccountRepository {
    private final Map<Long, Account> accounts = new HashMap<>();
    private long currentId = 1;

    @Override
    public void save(Account account) {
        account.setId(currentId++);
        accounts.put(account.getId(), account);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(accounts.get(id));
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void update(Account account) {
        accounts.put(account.getId(), account);
    }

    @Override
    public void deleteById(Long id) {
        accounts.remove(id);
    }
}
