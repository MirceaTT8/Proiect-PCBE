package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.model.entity.*;
import com.javazerozahar.stock_exchange.repository.AccountRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.AccountRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockHistoryRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.TransactionRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StockExchangeApplication {

	public static void main(String[] args) {
		// Create repository instances
		StockRepositoryImpl stockRepo = new StockRepositoryImpl();
		StockHistoryRepositoryImpl stockHistoryRepo = new StockHistoryRepositoryImpl();
		TransactionRepositoryImpl transactionRepo = new TransactionRepositoryImpl();
		AccountRepositoryImpl accountRepo = new AccountRepositoryImpl();

		// Adding some Stock entries
		Stock stock1 = Stock.builder().symbol("AAPL").price(150.0).quantity(100).build();
		Stock stock2 = Stock.builder().symbol("GOOGL").price(2800.0).quantity(50).build();

		stockRepo.save(stock1);
		stockRepo.save(stock2);

		// Fetch and display all stocks
		List<Stock> allStocks = stockRepo.findAll();
		System.out.println("Stocks:");
		allStocks.forEach(System.out::println);

		// Adding Stock history for a stock
		StockHistory history1 = StockHistory.builder().stockId(stock1.getId()).price(149.0).timestamp(System.currentTimeMillis()).build();
		StockHistory history2 = StockHistory.builder().stockId(stock1.getId()).price(151.0).timestamp(System.currentTimeMillis()).build();
		stockHistoryRepo.save(history1);
		stockHistoryRepo.save(history2);

		// Fetch and display stock history for stock1
		List<StockHistory> stock1History = stockHistoryRepo.findByStockId(stock1.getId());
		System.out.println("Stock History for AAPL:");
		stock1History.forEach(System.out::println);

		// Create and save some Account entries
		Account account1 = Account.builder()
				.userId(1L)
				.balance(10000.0)
				.portfolio(List.of(Portfolio.builder().stockId(stock1.getId()).quantity(20).build()))
				.build();

		Account account2 = Account.builder()
				.userId(2L)
				.balance(20000.0)
				.portfolio(List.of(Portfolio.builder().stockId(stock2.getId()).quantity(10).build()))
				.build();

		accountRepo.save(account1);
		accountRepo.save(account2);

		List<Account> allAccounts = accountRepo.findAll();
		System.out.println("Accounts:");
		allAccounts.forEach(System.out::println);

		Transaction transaction1 = Transaction.builder()
				.stockId(stock1.getId())
				.sellerId(account1.getUserId())
				.buyerId(account2.getUserId())
				.quantity(5)
				.price(150.0)
				.timestamp(System.currentTimeMillis())
				.build();

		Transaction transaction2 = Transaction.builder()
				.stockId(stock2.getId())
				.sellerId(account2.getUserId())
				.buyerId(account1.getUserId())
				.quantity(3)
				.price(2800.0)
				.timestamp(System.currentTimeMillis())
				.build();

		transactionRepo.save(transaction1);
		transactionRepo.save(transaction2);

		System.out.println("Transactions for AAPL:");
		List<Transaction> transactionsForAAPL = transactionRepo.findAllByStockId(stock1.getId());
		transactionsForAAPL.forEach(System.out::println);

		System.out.println("Transactions involving User with ID 1 (Account 1):");
		List<Transaction> transactionsByUser1 = transactionRepo.findAllByUserId(account1.getUserId());
		transactionsByUser1.forEach(System.out::println);


		Optional<Stock> stockToUpdate = stockRepo.findById(stock1.getId());
		if (stockToUpdate.isPresent()) {
			Stock stock = stockToUpdate.get();
			stock.setPrice(155.0);
			stockRepo.update(stock);
			System.out.println("Updated Stock: " + stockRepo.findById(stock.getId()).get());
		}

		// Update account balance after a transaction
		account1.setBalance(account1.getBalance() - transaction1.getPrice() * transaction1.getQuantity()); // Deduct buyer's cost
		accountRepo.update(account1);

		account2.setBalance(account2.getBalance() + transaction1.getPrice() * transaction1.getQuantity()); // Add seller's profit
		accountRepo.update(account2);

		account2.setBalance(account2.getBalance() + transaction2.getPrice() * transaction2.getQuantity()); // Deduct buyer's cost
		accountRepo.update(account2);

		account1.setBalance(account1.getBalance() - transaction2.getPrice() * transaction2.getQuantity()); // Add seller's profit
		accountRepo.update(account1);

		// Display updated account balances
		System.out.println("Updated Account Balances:");
		System.out.println("Account 1 (User 1): " + account1.getBalance());
		System.out.println("Account 2 (User 2): " + account2.getBalance());

		// Delete a transaction and display remaining transactions
		transactionRepo.deleteById(transaction1.getId());
		System.out.println("Transactions after deletion:");
		List<Transaction> allTransactions = transactionRepo.findAll();
		allTransactions.forEach(transaction -> System.out.println(transaction));
	}
}
