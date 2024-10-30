package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.handlers.OrderHandler;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.InetSocketAddress;

@Log4j2
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
