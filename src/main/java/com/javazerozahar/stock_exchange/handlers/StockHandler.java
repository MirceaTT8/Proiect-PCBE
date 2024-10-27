package com.javazerozahar.stock_exchange.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javazerozahar.stock_exchange.model.dto.StockDTO;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockRepositoryImpl;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

public class StockHandler implements HttpHandler {
    private final StockRepositoryImpl stockRepository = new StockRepositoryImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int statusCode;

        String requestMethod = exchange.getRequestMethod();
        String requestURI = exchange.getRequestURI().toString();

        switch (requestMethod) {
            case "GET" -> {
                if ("/stocks".equals(requestURI)) {
                    List<Stock> stocks = stockRepository.findAll();
                    response = objectMapper.writeValueAsString(stocks);
                    statusCode = 200;
                } else if (requestURI.startsWith("/stocks/")) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    Optional<Stock> stock = stockRepository.findById(Long.parseLong(id));
                    response = objectMapper.writeValueAsString(stock);
                    statusCode = 200;
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "POST" -> {
                if ("/stocks".equals(requestURI)) {
                    StockDTO stockDTO = readRequestBody(exchange);
                    response = "Received stock: " + stockDTO.getId();
                    statusCode = 201; // Created
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "PUT" -> {
                if ("/stocks/".equals(requestURI)) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    Optional<Stock> stock = stockRepository.findById(Long.parseLong(id));
                    if (stock.isPresent()) {
                        stock.ifPresent(stockRepository::update);
                        response = "Updated stock: " + Long.parseLong(id);
                        statusCode = 200;
                    } else {
                        response = "Stock Not Found";
                        statusCode = 404;
                    }
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "DELETE" -> {
                if ("/stocks/".equals(requestURI)) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    StockDTO stockDTO = readRequestBody(exchange);
                    stockRepository.deleteById(stockDTO.getId());
                    response = "Deleted stock: " + stockDTO.getId();
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

    private StockDTO readRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        return objectMapper.readValue(inputStream, StockDTO.class);
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
