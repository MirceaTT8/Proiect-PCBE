package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import com.javazerozahar.stock_exchange.repository.StockHistoryRepository;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import com.javazerozahar.stock_exchange.repository.TransactionRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.PortfolioRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockHistoryRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.TransactionRepositoryImpl;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;

import java.util.List;

public class Initialize {
        public static void start() {

            // Create repository instances
            StockRepository stockRepo = SingletonFactory.getInstance(StockRepositoryImpl.class);
            StockHistoryRepository stockHistoryRepo = SingletonFactory.getInstance(StockHistoryRepositoryImpl.class);
            TransactionRepository transactionRepo = SingletonFactory.getInstance(TransactionRepositoryImpl.class);
            PortfolioRepository portfolioRepository = SingletonFactory.getInstance(PortfolioRepositoryImpl.class);

            // Adding some Stock entries
            Stock stock1 = Stock.builder().symbol("AAPL").price(150.0).build();
            Stock stock2 = Stock.builder().symbol("GOOGL").price(2800.0).build();
            Stock stock3 = Stock.builder().symbol("$EUR").price(1.0).build();
            Stock stock4 = Stock.builder().symbol("MSFT").price(300.0).build();
            Stock stock5 = Stock.builder().symbol("AMZN").price(3500.0).build();
            Stock stock6 = Stock.builder().symbol("TSLA").price(800.0).build();
            Stock stock7 = Stock.builder().symbol("FB").price(320.0).build();

            // Saving stocks
            stockRepo.save(stock1);
            stockRepo.save(stock2);
            stockRepo.save(stock3);
            stockRepo.save(stock4);
            stockRepo.save(stock5);
            stockRepo.save(stock6);
            stockRepo.save(stock7);

            // Fetch and display all stocks
            List<Stock> allStocks = stockRepo.findAll();
            System.out.println("Stocks:");
            allStocks.forEach(System.out::println);

            // Adding Stock history for AAPL and MSFT
            StockHistory history1 = StockHistory.builder().stockId(stock1.getId()).price(149.0).timestamp(System.currentTimeMillis()).build();
            StockHistory history2 = StockHistory.builder().stockId(stock1.getId()).price(151.0).timestamp(System.currentTimeMillis()).build();
            StockHistory history3 = StockHistory.builder().stockId(stock4.getId()).price(299.0).timestamp(System.currentTimeMillis()).build();
            StockHistory history4 = StockHistory.builder().stockId(stock4.getId()).price(305.0).timestamp(System.currentTimeMillis()).build();

            stockHistoryRepo.save(history1);
            stockHistoryRepo.save(history2);
            stockHistoryRepo.save(history3);
            stockHistoryRepo.save(history4);

            // Fetch and display stock history for stock1
            List<StockHistory> stock1History = stockHistoryRepo.findByStockId(stock1.getId());
            System.out.println("Stock History for AAPL:");
            stock1History.forEach(System.out::println);

            // Adding more user portfolios
            List<Portfolio> portfolios = List.of(
                    Portfolio.builder().userId(1L).quantity(10.0).stockId(stock1.getId()).build(),
                    Portfolio.builder().userId(1L).quantity(50.0).stockId(stock4.getId()).build(),
                    Portfolio.builder().userId(2L).quantity(100.0).stockId(stock1.getId()).build(),
                    Portfolio.builder().userId(2L).quantity(25.0).stockId(stock5.getId()).build(),
                    Portfolio.builder().userId(3L).quantity(1000.0).stockId(stock3.getId()).build(),
                    Portfolio.builder().userId(3L).quantity(200.0).stockId(stock6.getId()).build(),
                    Portfolio.builder().userId(4L).quantity(500.0).stockId(stock7.getId()).build()
            );

            portfolios.forEach(portfolioRepository::save);

//            List<Account> allAccounts = accountRepo.findAll();
//            System.out.println("Accounts:");
//            allAccounts.forEach(System.out::println);
//
//            Transaction transaction1 = Transaction.builder()
//                    .stockId(stock1.getId())
//                    .sellerId(account1.getUserId())
//                    .buyerId(account2.getUserId())
//                    .quantity(5)
//                    .price(150.0)
//                    .timestamp(System.currentTimeMillis())
//                    .build();
//
//            Transaction transaction2 = Transaction.builder()
//                    .stockId(stock2.getId())
//                    .sellerId(account2.getUserId())
//                    .buyerId(account1.getUserId())
//                    .quantity(3)
//                    .price(2800.0)
//                    .timestamp(System.currentTimeMillis())
//                    .build();

//            transactionRepo.save(transaction1);
//            transactionRepo.save(transaction2);

//            System.out.println("Transactions for AAPL:");
//            List<Transaction> transactionsForAAPL = transactionRepo.findAllByStockId(stock1.getId());
//            transactionsForAAPL.forEach(System.out::println);
//
//            System.out.println("Transactions involving User with ID 1 (Account 1):");
//            List<Transaction> transactionsByUser1 = transactionRepo.findAllByUserId(account1.getUserId());
//            transactionsByUser1.forEach(System.out::println);
//
//            Optional<Stock> stockToUpdate = stockRepo.findById(stock1.getId());
//            if (stockToUpdate.isPresent()) {
//                Stock stock = stockToUpdate.get();
//                stock.setPrice(155.0);
//                stockRepo.update(stock);
//                System.out.println("Updated Stock: " + stockRepo.findById(stock.getId()).get());
//            }

            // Update account balance after a transaction
//            account1.setBalance(account1.getBalance() - transaction1.getPrice() * transaction1.getQuantity()); // Deduct buyer's cost
//            accountRepo.update(account1);
//
//            account2.setBalance(account2.getBalance() + transaction1.getPrice() * transaction1.getQuantity()); // Add seller's profit
//            accountRepo.update(account2);
//
//            account2.setBalance(account2.getBalance() + transaction2.getPrice() * transaction2.getQuantity()); // Deduct buyer's cost
//            accountRepo.update(account2);
//
//            account1.setBalance(account1.getBalance() - transaction2.getPrice() * transaction2.getQuantity()); // Add seller's profit
//            accountRepo.update(account1);
//
//            // Display updated account balances
//            System.out.println("Updated Account Balances:");
//            System.out.println("Account 1 (User 1): " + account1.getBalance());
//            System.out.println("Account 2 (User 2): " + account2.getBalance());
//
//            // Delete a transaction and display remaining transactions
//            transactionRepo.deleteById(transaction1.getId());
//            System.out.println("Transactions after deletion:");
//            List<Transaction> allTransactions = transactionRepo.findAll();
//            allTransactions.forEach(System.out::println);
        }
}
