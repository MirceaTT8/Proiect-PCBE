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
    private final OrderRepository orderRepository;

    public void initialize() {

        User user1 = User.builder()
                .lastName("John Doe")
                .fistName("John")
                .email("johndoe@example.com")
                .phoneNumber("+40712345678")
                .build();

        User user2 = User.builder()
                .lastName("Jane Smith")
                .fistName("Jane")
                .email("janesmith@example.com")
                .phoneNumber("+40720456789")
                .build();

        User user3 = User.builder()
                .lastName("Alice Johnson")
                .fistName("Alice")
                .email("alicejohnson@example.com")
                .phoneNumber("+40730123456")
                .build();

        List<User> users = List.of(user1, user2, user3);
        userRepository.saveAll(users);

        // Adding some Stock entries
        Stock stock1 = Stock.builder().id(1L).symbol("AAPL").price(150.0).build();
        Stock stock2 = Stock.builder().id(2L).symbol("GOOGL").price(2800.0).build();
        Stock stock3 = Stock.builder().id(3L).symbol("$EUR").price(1.0).build();
        Stock stock4 = Stock.builder().id(4L).symbol("TSLA").price(255.0).build();
        Stock stock5 = Stock.builder().id(5L).symbol("NVDA").price(137.0).build();
        Stock stock6 = Stock.builder().id(6L).symbol("META").price(570.0).build();

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
        StockHistory history1 = StockHistory.builder()
                .stock(stock1)
                .price(149.0)
                .timestamp(System.currentTimeMillis())
                .build();
        StockHistory history2 = StockHistory.builder()
                .stock(stock1)
                .price(151.0)
                .timestamp(System.currentTimeMillis())
                .build();

        stockHistoryRepository.saveAll(List.of(history1, history2));

        // Fetch and display stock history for stock1
        List<StockHistory> stock1History = stockHistoryRepository.findByStockId(stock1.getId());
        //System.out.println("Stock History for AAPL:");

        List<Portfolio> portfolios = List.of(
                Portfolio.builder()
                        .user(user1)
                        .quantity(0.0)
                        .stock(stock1)
                        .build(),
                Portfolio.builder()
                        .user(user2)
                        .quantity(100.0)
                        .stock(stock1)
                        .build(),
                Portfolio.builder()
                        .user(user1)
                        .quantity(10000.0)
                        .stock(stock3)
                        .build(),
                Portfolio.builder()
                        .user(user2)
                        .quantity(10000.0)
                        .stock(stock3)
                        .build()
        );

        portfolioRepository.saveAll(portfolios);

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
        orderRepository.deleteAll();
        userRepository.deleteAll();
        stockHistoryRepository.deleteAll();
        stockRepository.deleteAll();
    }
}
