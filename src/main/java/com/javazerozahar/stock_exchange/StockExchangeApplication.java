package com.javazerozahar.stock_exchange;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Log4j2
@RequiredArgsConstructor
public class StockExchangeApplication {

	@Value("${stockexchange.initialize-db}")
	private boolean initializeDb;

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StockExchangeApplication.class, args);

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			executorService.shutdown();
			System.out.println("Application shutdown.");
		}));
	}

	@Bean
	public ApplicationListener<ApplicationReadyEvent> initializeDatabase(Initializer initializer) {
		return _ -> {
			if (initializeDb) {
				initializer.initialize();
			}
		};
	}
}
