package com.javazerozahar.stock_exchange.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.javazerozahar.stock_exchange.converters.StockHistoryConverter;
import com.javazerozahar.stock_exchange.model.dto.StockHistoryDTO;
import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockHistoryRepositoryImpl;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

public class StockHistoryHandler implements HttpHandler {
    private final StockHistoryRepositoryImpl stockHistoryRepository = new StockHistoryRepositoryImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StockHistoryConverter stockHistoryConverter = new StockHistoryConverter();

    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int statusCode;

        String requestMethod = exchange.getRequestMethod();
        String requestURI = exchange.getRequestURI().toString();

        switch (requestMethod) {
            case "GET" -> {
                if ((requestURI.startsWith("/stock_history/stock/"))) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    List<StockHistory> stockHistory = stockHistoryRepository.findByStockId(Long.parseLong(id));
                    response = objectMapper.writeValueAsString(stockHistory);
                    statusCode = 200;
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "POST" -> {
                if ("/stock_history".equals(requestURI)) {
                    StockHistoryDTO stockHistoryDTO = readRequestBody(exchange);
                    stockHistoryRepository.save(stockHistoryConverter.toStockHistory(stockHistoryDTO));
                    response = "Received stock history: " + stockHistoryDTO.getStockId();
                    statusCode = 201; // Created
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

    private StockHistoryDTO readRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        return objectMapper.readValue(inputStream, StockHistoryDTO.class);
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
