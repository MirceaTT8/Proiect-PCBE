package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.handlers.OrderHandler;
import com.javazerozahar.stock_exchange.rabbit.general.RabbitMQConsumer;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class StockExchangeApplication {

	public static void main(String[] args) {
		Initialize.start();

		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.submit(StockExchangeApplication::startHttpServer);

		executorService.submit(() -> {
			RabbitMQConsumer rabbitConsumer = new RabbitMQConsumer();
			rabbitConsumer.receiveMessages();
		});

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			executorService.shutdown();
			System.out.println("Application shutdown.");
		}));
	}

	private static void startHttpServer() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);
			server.createContext("/orders", SingletonFactory.getInstance(OrderHandler.class));
			server.setExecutor(Executors.newFixedThreadPool(10));  // Configure HTTP server thread pool
			server.start();
			System.out.println("HTTP server started on port 3000");
		} catch (IOException e) {
			System.err.println("Cannot start HTTP server: " + e.getMessage());
		}
	}
}
