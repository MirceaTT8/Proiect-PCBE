package com.javazerozahar.stock_exchange.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.service.OrderService;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class OrderHandler implements HttpHandler {

    private final OrderService orderService;

    public OrderHandler() {
        this.orderService = SingletonFactory.getInstance(OrderService.class);
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            String response = "";

            try {
                if (exchange.getRequestMethod().equals("PUT")) {
                    response = handlePutRequest(exchange);
                } else if (exchange.getRequestMethod().equals("POST")) {
                    response = handlePostRequest(exchange);
                } else if (exchange.getRequestMethod().equals("DELETE")) {
                    response = handleDeleteRequest(exchange);
                } else {
                    throw new IllegalArgumentException("Unsupported HTTP method: " + exchange.getRequestMethod());
                }
            } catch (IllegalArgumentException e) {
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            }

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String handleDeleteRequest(HttpExchange exchange) throws IOException {
        return handleRequest(exchange, "delete");
    }

    private String handlePutRequest(HttpExchange exchange) throws IOException {
        return handleRequest(exchange, "update");
    }

    private String handlePostRequest(HttpExchange exchange) throws IOException {
        return handleRequest(exchange, "create");
    }

    private String handleRequest(HttpExchange exchange, String orderStrategy) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        byte[] requestBodyBytes = inputStream.readAllBytes();
        String requestBody = new String(requestBodyBytes, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OrderDTO order = objectMapper.readValue(requestBody, OrderDTO.class);

            orderService.placeOrder(order, orderStrategy);

            return objectMapper.writeValueAsString(order);
        } catch (Exception e) {

            if (e.getMessage().equals("Missing required fields")) {
                e.printStackTrace();
                System.out.println("Bad Request: Invalid JSON or missing fields");
            }

            e.printStackTrace();

            String errorMessage = "Bad Request: Invalid JSON or missing fields";
            exchange.sendResponseHeaders(400, errorMessage.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(errorMessage.getBytes());
            os.close();
            return null;
        }
    }
}
