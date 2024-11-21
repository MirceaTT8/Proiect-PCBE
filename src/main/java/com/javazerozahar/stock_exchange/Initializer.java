package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.model.entity.StockHistory;
import com.javazerozahar.stock_exchange.model.entity.User;
import com.javazerozahar.stock_exchange.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
public class Initializer {

    private final StockRepository stockRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final TransactionRepository transactionRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    public void initialize() {

        // Create repository instances
        List<User> users = List.of(new User[]{
                User.builder().id(1L).build(),
                User.builder().id(2L).build(),
                User.builder().id(3L).build()
        });

        userRepository.saveAll(users);

        // Adding some Stock entries
        Stock stock1 = Stock.builder().symbol("AAPL").price(150.0).build();
        Stock stock2 = Stock.builder().symbol("GOOGL").price(2800.0).build();
        Stock stock3 = Stock.builder().symbol("$EUR").price(1.0).build();
        Stock stock4 = Stock.builder().symbol("TSLA").price(255.0).build();
        Stock stock5 = Stock.builder().symbol("NVDA").price(137.0).build();
        Stock stock6 = Stock.builder().symbol("META").price(570.0).build();

        stockRepository.save(stock1);
        stockRepository.save(stock2);
        stockRepository.save(stock3);
        stockRepository.save(stock4);
        stockRepository.save(stock5);
        stockRepository.save(stock6);

        // Fetch and display all stocks
        List<Stock> allStocks = stockRepository.findAll();
        //System.out.println("Stocks:");
        //allStocks.forEach(System.out::println);

        // Adding Stock history for a stock
        StockHistory history1 = StockHistory.builder().stock(stock1).price(149.0).timestamp(System.currentTimeMillis()).build();
        StockHistory history2 = StockHistory.builder().stock(stock1).price(151.0).timestamp(System.currentTimeMillis()).build();
        stockHistoryRepository.save(history1);
        stockHistoryRepository.save(history2);

        // Fetch and display stock history for stock1
        List<StockHistory> stock1History = stockHistoryRepository.findByStockId(stock1.getId());
        //System.out.println("Stock History for AAPL:");

        List<Portfolio> portfolios = List.of(new Portfolio[]{
                Portfolio.builder()
                        .user(userRepository.findById(1L).get())
                        .quantity(0.0)
                        .stock(stockRepository.findById(1L).get())
                        .build(),

                Portfolio.builder()
                        .user(userRepository.findById(2L).get())
                        .quantity(100.0)
                        .stock(stockRepository.findById(1L).get())
                        .build(),

                Portfolio.builder()
                        .user(userRepository.findById(1L).get())
                        .quantity(10000.0)
                        .stock(stockRepository.findById(3L).get())
                        .build(),

                Portfolio.builder()
                        .user(userRepository.findById(2L).get())
                        .quantity(10000.0)
                        .stock(stockRepository.findById(3L).get())
                        .build()
        });

        portfolioRepository.saveAll(portfolios);
//
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

    public void reset() {
        portfolioRepository.deleteAll();
        transactionRepository.deleteAll();
        userRepository.deleteAll();
        stockHistoryRepository.deleteAll();
        stockRepository.deleteAll();
    }
}
