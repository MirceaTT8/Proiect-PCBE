package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.OrderRepository;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import com.javazerozahar.stock_exchange.repository.StockRepository;
import com.javazerozahar.stock_exchange.repository.TransactionRepository;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.OrderRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.PortfolioRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.StockRepositoryImpl;
import com.javazerozahar.stock_exchange.repository.repositoryImpl.TransactionRepositoryImpl;
import com.javazerozahar.stock_exchange.service.OrderService;
import com.javazerozahar.stock_exchange.service.PortfolioService;
import com.javazerozahar.stock_exchange.service.StockService;
import com.javazerozahar.stock_exchange.service.TransactionService;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import org.junit.jupiter.api.*;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class StockExchangeApplicationTests {

	private OrderService orderService;
	private PortfolioService portfolioService;
	private StockService stockService;
	private TransactionService transactionService;

	private OrderRepository orderRepository;
	private StockRepository stockRepository;
	private PortfolioRepository portfolioRepository;
	private TransactionRepository transactionRepository;

	@BeforeAll
	public static void start() {
		StockExchangeApplication.main(null);

	}

	@BeforeEach
	public void prepare() {
		this.orderService = SingletonFactory.getInstance(OrderService.class);
		this.portfolioService = SingletonFactory.getInstance(PortfolioService.class);
		this.stockService = SingletonFactory.getInstance(StockService.class);
		this.transactionService = SingletonFactory.getInstance(TransactionService.class);

		this.orderRepository = SingletonFactory.getInstance(OrderRepositoryImpl.class);
		this.stockRepository = SingletonFactory.getInstance(StockRepositoryImpl.class);
		this.portfolioRepository = SingletonFactory.getInstance(PortfolioRepositoryImpl.class);
		this.transactionRepository = SingletonFactory.getInstance(TransactionRepositoryImpl.class);

		Initialize.start();
	}

	@Test
	void testSellMatchesBuyOrderSequential() {

		OrderDTO order1 = OrderDTO.builder()
				.orderId(null)
				.userId(1L)
				.soldStockId(3L)
				.boughtStockId(1L)
				.price(5.0)
				.quantity(10.0)
				.orderType(OrderType.BUY)
				.build();

		OrderDTO order2 = OrderDTO.builder()
				.orderId(null)
				.userId(1L)
				.soldStockId(3L)
				.boughtStockId(1L)
				.price(5.0)
				.quantity(10.0)
				.orderType(OrderType.BUY)
				.build();

		OrderDTO order3 = OrderDTO.builder()
				.orderId(null)
				.userId(2L)
				.soldStockId(1L)
				.boughtStockId(3L)
				.price(5.0)
				.quantity(15.0)
				.orderType(OrderType.SELL)
				.build();

		orderService.placeOrder(order1, "create");
		orderService.placeOrder(order2, "create");

		assertEquals(9900.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(3L)).getQuantity());

		orderService.placeOrder(order3, "create");

		assertEquals(85.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(1L)).getQuantity());

		assertEquals(10075.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(3L)).getQuantity());
		assertEquals(15.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(1L)).getQuantity());
	}

	@Test
	void testBuyMatchesSellOrderSequential() {

		assertEquals(100.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(1L)).getQuantity());

		OrderDTO order1 = OrderDTO.builder()
				.orderId(null)
				.userId(1L)
				.soldStockId(3L)
				.boughtStockId(1L)
				.price(5.0)
				.quantity(10.0)
				.orderType(OrderType.BUY)
				.build();

		OrderDTO order2 = OrderDTO.builder()
				.orderId(null)
				.userId(1L)
				.soldStockId(3L)
				.boughtStockId(1L)
				.price(5.0)
				.quantity(10.0)
				.orderType(OrderType.BUY)
				.build();

		OrderDTO order3 = OrderDTO.builder()
				.orderId(null)
				.userId(2L)
				.soldStockId(1L)
				.boughtStockId(3L)
				.price(5.0)
				.quantity(15.0)
				.orderType(OrderType.SELL)
				.build();

		orderService.placeOrder(order3, "create");
		assertEquals(85.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(1L)).getQuantity());

		orderService.placeOrder(order1, "create");

		assertEquals(9950.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(3L)).getQuantity());
		assertEquals(10.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(1L)).getQuantity());

		orderService.placeOrder(order2, "create");
		assertEquals(9900.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(3L)).getQuantity());

		assertEquals(15.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(1L)).getQuantity());
		assertEquals(10075.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(3L)).getQuantity());
	}

	@Test
	public void testUpdateBuyOrderChangePriceAndQuantitySequential() {
		OrderDTO order1 = OrderDTO.builder()
				.orderId(null)
				.userId(1L)
				.soldStockId(3L)
				.boughtStockId(1L)
				.price(5.0)
				.quantity(10.0)
				.orderType(OrderType.BUY)
				.build();

		orderService.placeOrder(order1, "create");

		assertEquals(9950.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(3L)).getQuantity());

		order1.setOrderId(2L);
		order1.setQuantity(20.0);
		order1.setPrice(10.0);

		orderService.placeOrder(order1, "update");

		assertEquals(9800.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(3L)).getQuantity());
	}

	@Test
	public void testUpdateSellOrderChangePriceAndQuantitySequential() {

		double initialQuantity = portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(1L)).getQuantity();

		OrderDTO order = OrderDTO.builder()
				.orderId(null)
				.userId(2L)
				.soldStockId(1L)
				.boughtStockId(3L)
				.price(5.0)
				.quantity(15.0)
				.orderType(OrderType.SELL)
				.build();

		orderService.placeOrder(order, "create");

		assertEquals(initialQuantity - 15.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(1L)).getQuantity());

		order.setOrderId(2L);
		order.setQuantity(20.0);
		order.setPrice(10.0);

		orderService.placeOrder(order, "update");

		assertEquals(initialQuantity - 20.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(1L)).getQuantity());
	}

//	@Test
//	@DisplayName("Test Buy Matches Sell Order Sequential")
//	void testBuyMatchesSellOrderSequential() {
//		// Existing test implementation
//	}

	@Test
	@DisplayName("Test Stock Service - Get Stock")
	void testGetStock() {
		Stock stock = stockService.getStock(1L);
		assertNotNull(stock);
		assertEquals(1L, stock.getId());
	}

	@Test
	@DisplayName("Test Portfolio Service - Get Portfolio")
	void testGetPortfolio() {
		Portfolio portfolio = portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(1L));
		assertNotNull(portfolio);
		assertEquals(1L, portfolio.getUserId());
	}

	@Test
	@DisplayName("Test Portfolio Service - Update Portfolio")
	void testUpdatePortfolio() {
		Stock boughtStock = stockService.getStock(1L);
		Stock soldStock = stockService.getStock(3L);
		Portfolio initialPortfolio = portfolioService.getPortfolioByUserIdAndStock(1L, boughtStock);
		double initialQuantity = initialPortfolio.getQuantity();

		Order testOrder = Order.builder()
				.orderId(1L)
				.userId(1L)
				.soldStock(soldStock)
				.boughtStock(boughtStock)
				.price(10.0)
				.quantity(5.0)
				.orderType(OrderType.SELL)
				.build();

		// Execute
		portfolioService.updatePortfolio(testOrder, 5.0);

		// Verify
		Portfolio updatedPortfolio = portfolioService.getPortfolioByUserIdAndStock(1L, boughtStock);
		assertEquals(initialQuantity + 5.0, updatedPortfolio.getQuantity(),
				"Portfolio quantity for bought stock should increase by 5.0 for a SELL order");
	}

	@Test
	@DisplayName("Test Transaction Service - Create Transaction")
	void testCreateTransaction() {
		Order buyOrder = Order.builder()
				.orderId(1L)
				.userId(1L)
				.soldStock(stockService.getStock(3L))
				.boughtStock(stockService.getStock(1L))
				.price(5.0)
				.quantity(10.0)
				.orderType(OrderType.BUY)
				.build();

		Order sellOrder = Order.builder()
				.orderId(2L)
				.userId(2L)
				.soldStock(stockService.getStock(1L))
				.boughtStock(stockService.getStock(3L))
				.price(5.0)
				.quantity(10.0)
				.orderType(OrderType.SELL)
				.build();

		transactionService.createTransaction(buyOrder, sellOrder, 10.0);

		Portfolio buyerPortfolio = portfolioService.getPortfolioByUserIdAndStock(1L, buyOrder.getBoughtStock());
		Portfolio sellerPortfolio = portfolioService.getPortfolioByUserIdAndStock(2L, sellOrder.getBoughtStock());

		assertNotNull(buyerPortfolio);
		assertNotNull(sellerPortfolio);
	}

	@Test
	@DisplayName("Test Order Service - Place Order")
	void testPlaceOrder() {
		OrderDTO testOrder = OrderDTO.builder()
				.orderId(null)
				.userId(1L)
				.soldStockId(3L)
				.boughtStockId(1L)
				.price(5.0)
				.quantity(10.0)
				.orderType(OrderType.BUY)
				.build();

		orderService.placeOrder(testOrder, "create");

		Portfolio portfolio = portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(3L));
		assertNotNull(portfolio);
		assertEquals(9950.0, portfolio.getQuantity());
	}

	@Test
	@DisplayName("Test Invalid Stock ID")
	void testInvalidStockId() {
		assertThrows(RuntimeException.class, () -> {
			stockService.getStock(999L);
		});
	}
	@Test
	void testConcurrentBuyAndSellOrders() throws InterruptedException {
		int numberOfPairs = 5;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfPairs * 2);
		CountDownLatch latch = new CountDownLatch(numberOfPairs * 2);

		for (int i = 0; i < numberOfPairs; i++) {
			// Submit buy order
			executorService.submit(() -> {
				try {
					OrderDTO buyOrder = OrderDTO.builder()
							.userId(1L)
							.soldStockId(3L)
							.boughtStockId(1L)
							.price(5.0)
							.quantity(10.0)
							.orderType(OrderType.BUY)
							.build();

					sendOrder(buyOrder);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					latch.countDown();
				}
			});

			// Submit matching sell order
			executorService.submit(() -> {
				try {
					OrderDTO sellOrder = OrderDTO.builder()
							.userId(2L)
							.soldStockId(1L)
							.boughtStockId(3L)
							.price(5.0)
							.quantity(10.0)
							.orderType(OrderType.SELL)
							.build();

					sendOrder(sellOrder);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await(10, TimeUnit.SECONDS);
		executorService.shutdown();

		// Verify the final state
		Portfolio user1Portfolio = portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(1L));
		Portfolio user2Portfolio = portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(3L));

		assertNotNull(user1Portfolio);
		assertNotNull(user2Portfolio);
	}

	private void sendOrder(OrderDTO buyOrder) {
	}

	@RepeatedTest(50)
	void testBuyMatchesSellOrderConcurrent() throws ExecutionException, InterruptedException {
		assertEquals(100.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(1L)).getQuantity());

		OrderDTO order1 = OrderDTO.builder()
				.orderId(null)
				.userId(1L)
				.soldStockId(3L)
				.boughtStockId(1L)
				.price(5.0)
				.quantity(10.0)
				.orderType(OrderType.BUY)
				.build();

		OrderDTO order3 = OrderDTO.builder()
				.orderId(null)
				.userId(2L)
				.soldStockId(1L)
				.boughtStockId(3L)
				.price(5.0)
				.quantity(15.0)
				.orderType(OrderType.SELL)
				.build();

		OrderDTO order2 = OrderDTO.builder()
				.orderId(2L)
				.userId(1L)
				.soldStockId(3L)
				.boughtStockId(1L)
				.price(5.0)
				.quantity(10.0)
				.orderType(OrderType.BUY)
				.build();


		CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> orderService.placeOrder(order3, "create"));
		CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> orderService.placeOrder(order1, "create"));
		CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> orderService.placeOrder(order2, "update"));

		CompletableFuture.allOf(future1, future2, future3).get();

		assertEquals(15.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(1L)).getQuantity());
		assertEquals(10075.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(3L)).getQuantity());
	}

	@AfterEach
	public void resetDatabase() {
		orderRepository.reset();
		transactionRepository.reset();
		portfolioRepository.reset();
		stockRepository.reset();
	}
}