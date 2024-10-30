package com.javazerozahar.stock_exchange.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javazerozahar.stock_exchange.model.dto.PortfolioDTO;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.PortfolioRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

public class PortfolioHandler implements HttpHandler {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PortfolioRepository portfolioRepository = SingletonFactory.getInstance(PortfolioRepositoryImpl.class);

    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int statusCode;

        String requestMethod = exchange.getRequestMethod();
        String requestURI = exchange.getRequestURI().toString();

        switch (requestMethod) {
            case "GET" -> {
                if ("/portfolios".equals(requestURI)) {
                    List<Portfolio> portfolios = portfolioRepository.findAll();
                    response = objectMapper.writeValueAsString(portfolios);
                    statusCode = 200;
                } else if (requestURI.startsWith("/portfolios/")) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    Optional<Portfolio> portfolio = portfolioRepository.findById(Long.parseLong(id));
                    response = objectMapper.writeValueAsString(portfolio);
                    statusCode = 200;
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "POST" -> {
                if ("/portfolios".equals(requestURI)) {
                    PortfolioDTO portfolioDTO = readRequestBody(exchange);
                    response = "Received portfolio: " + portfolioDTO.getId();
                    statusCode = 201; // Created
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "PUT" -> {
                if ("/portfolios/".startsWith(requestURI)) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    Optional<Portfolio> portfolio = portfolioRepository.findById(Long.parseLong(id));
                    if (portfolio.isPresent()) {
                        portfolio.ifPresent(portfolioRepository::update);
                        response = "Updated portfolio: " + Long.parseLong(id);
                        statusCode = 200;
                    } else {
                        response = "portfolio Not Found";
                        statusCode = 404;
                    }
                } else {
                    response = "Not Found";
                    statusCode = 404;
                }
            }
            case "DELETE" -> {
                if ("/portfolios/".startsWith(requestURI)) {
                    String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
                    portfolioRepository.deleteById(Long.parseLong(id));
                    response = "Deleted portfolio: " + Long.parseLong(id);
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

    private PortfolioDTO readRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        return objectMapper.readValue(inputStream, PortfolioDTO.class);
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
    

}
