package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.rabbit.order.OrderPlacerConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Log4j2
@RequiredArgsConstructor
public class StockExchangeApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StockExchangeApplication.class, args);

		context.getBean(Initializer.class).initialize();

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		executorService.submit(() -> {
			context.getBean(OrderPlacerConsumer.class).startListening();
		});

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			executorService.shutdown();
			System.out.println("Application shutdown.");
		}));
	}

}
