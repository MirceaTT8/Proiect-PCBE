package com.javazerozahar.stock_exchange.controller;

import com.javazerozahar.stock_exchange.model.entity.Account;
import com.javazerozahar.stock_exchange.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:4200")

public class AccountAPI {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public List<Account> findAllAccounts() { return accountRepository.findAll(); }

    @GetMapping("/get/{id}")
    public Optional<Account> findAccountById(@PathVariable("id") Long id) { return accountRepository.findById(id); }

    @PostMapping("/update")
    public void updateAccount(@RequestBody Account account) { accountRepository.update(account); }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable("id") Long id) { accountRepository.deleteById(id); }

}
