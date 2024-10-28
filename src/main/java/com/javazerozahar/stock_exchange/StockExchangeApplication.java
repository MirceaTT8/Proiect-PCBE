package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.handlers.OrderHandler;
import com.javazerozahar.stock_exchange.service.OrderService;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class StockExchangeApplication {

	public static void main(String[] args) {

		Initialize.start();

		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);
			server.createContext("/orders", SingletonFactory.getInstance(OrderHandler.class));
			server.start();
		} catch (IOException e) {
			System.out.println("Cannot start HTTP server");
		}

	}
}
