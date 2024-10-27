package com.javazerozahar.stock_exchange.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.javazerozahar.stock_exchange.converters.TransactionConverter;
import com.javazerozahar.stock_exchange.model.dto.TransactionDTO;
import com.javazerozahar.stock_exchange.model.entity.Transaction;
import com.javazerozahar.stock_exchange.repository.TransactionRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.TransactionRepositoryImpl;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

public class TransactionHandler implements HttpHandler {
    private final TransactionRepositoryImpl transactionRepository = new TransactionRepositoryImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TransactionConverter transactionConverter = new TransactionConverter();

    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int statusCode;

        String requestMethod = exchange.getRequestMethod();
        String requestURI = exchange.getRequestURI().toString();

        switch (requestMethod) {
            case "GET" -> {
                if ("/transactions".equals(requestURI)) {
                    List<Transaction> accounts = transactionRepository.findAll();
                    response = objectMapper.writeValueAsString(accounts);
                    statusCode = 200;
                } else if (requestURI.startsWith("/transactions/")) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    Optional<Transaction> transaction = transactionRepository.findById(Long.parseLong(id));
                    response = objectMapper.writeValueAsString(transaction);
                    statusCode = 200;
                } else if (requestURI.startsWith("/transactions/stock/")) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    List<Transaction> transactions = transactionRepository.findAllByStockId(Long.parseLong(id));
                    response = objectMapper.writeValueAsString(transactions);
                    statusCode = 200;
                } else if (requestURI.startsWith("/transactions/user/")) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    List<Transaction> transactions = transactionRepository.findAllByUserId(Long.parseLong(id));
                    response = objectMapper.writeValueAsString(transactions);
                    statusCode = 200;
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "POST" -> {
                if ("/transactions".equals(requestURI)) {
                    TransactionDTO transactionDTO = readRequestBody(exchange);
                    transactionRepository.save(transactionConverter.toTransaction(transactionDTO));
                    response = "Received transaction: " + transactionDTO.getId();
                    statusCode = 201; // Created
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "PUT" -> {
                if ("/transactions/".equals(requestURI)) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    Optional<Transaction> transaction = transactionRepository.findById(Long.parseLong(id));
                    if (transaction.isPresent()) {
                        transaction.ifPresent(transactionRepository::update);
                        response = "Updated transaction: " + Long.parseLong(id);
                        statusCode = 200;
                    } else {
                        response = "Transaction Not Found";
                        statusCode = 404;
                    }
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "DELETE" -> {
                if ("/transactions/".equals(requestURI)) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    transactionRepository.deleteById(Long.parseLong(id));
                    response = "Deleted transaction: " + Long.parseLong(id);
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

    private TransactionDTO readRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        return objectMapper.readValue(inputStream, TransactionDTO.class);
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
