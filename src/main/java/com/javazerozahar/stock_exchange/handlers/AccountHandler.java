package com.javazerozahar.stock_exchange.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javazerozahar.stock_exchange.converters.AccountConverter;
import com.javazerozahar.stock_exchange.model.dto.AccountDTO;
import com.javazerozahar.stock_exchange.model.entity.Account;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.AccountRepositoryImpl;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.*;
import java.util.List;
import java.util.Optional;

public class AccountHandler implements HttpHandler {
    private final AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AccountConverter accountConverter = new AccountConverter();

    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int statusCode;

        String requestMethod = exchange.getRequestMethod();
        String requestURI = exchange.getRequestURI().toString();

        switch (requestMethod) {
            case "GET" -> {
                if ("/accounts".equals(requestURI)) {
                    List<Account> accounts = accountRepository.findAll();
                    response = objectMapper.writeValueAsString(accounts);
                    statusCode = 200;
                } else if (requestURI.startsWith("/accounts/")) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    Optional<Account> account = accountRepository.findById(Long.parseLong(id));
                    response = objectMapper.writeValueAsString(account);
                    statusCode = 200;
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "POST" -> {
                if ("/accounts".equals(requestURI)) {
                    AccountDTO accountDTO = readRequestBody(exchange);
                    accountRepository.save(accountConverter.toAccount(accountDTO));
                    response = "Received account: " + accountDTO.getId();
                    statusCode = 201; // Created
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "PUT" -> {
                if ("/accounts/".equals(requestURI)) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    Optional<Account> account = accountRepository.findById(Long.parseLong(id));
                    if (account.isPresent()) {
                        account.ifPresent(accountRepository::update);
                        response = "Updated account: " + Long.parseLong(id);
                        statusCode = 200;
                    } else {
                        response = "Account Not Found";
                        statusCode = 404;
                    }
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "DELETE" -> {
                if ("/accounts/".equals(requestURI)) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    accountRepository.deleteById(Long.parseLong(id));
                    response = "Deleted account: " + Long.parseLong(id);
                    statusCode = 200;
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case null, default -> {
                response = "Method not supported";
                statusCode = 405; // Method Not Allowed
            }
        }
        sendResponse(exchange, response, statusCode);
    }

    private AccountDTO readRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        return objectMapper.readValue(inputStream, AccountDTO.class);
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
