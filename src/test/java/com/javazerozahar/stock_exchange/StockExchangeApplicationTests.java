package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.service.OrderService;
import com.javazerozahar.stock_exchange.service.PortfolioService;
import com.javazerozahar.stock_exchange.service.StockService;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StockExchangeApplicationTests {

	private OrderService orderService;
	private PortfolioService portfolioService;
	private StockService stockService;

	@BeforeEach
	public void prepare() {
		StockExchangeApplication.main(null);
		this.orderService = SingletonFactory.getInstance(OrderService.class);
		this.portfolioService = SingletonFactory.getInstance(PortfolioService.class);
		this.stockService = SingletonFactory.getInstance(StockService.class);
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

		assertEquals(14925.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(1L)).getQuantity());

		assertEquals(10075.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(3L)).getQuantity());
		assertEquals(15.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(1L)).getQuantity());
	}

	@Test
	void testBuyMatchesSellOrderSequential() {

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
		assertEquals(14925.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(1L)).getQuantity());

		orderService.placeOrder(order1, "create");

		assertEquals(9950.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(3L)).getQuantity());
		assertEquals(10.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(1L)).getQuantity());

		orderService.placeOrder(order2, "create");
		assertEquals(9900.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(3L)).getQuantity());

		assertEquals(10075.0, portfolioService.getPortfolioByUserIdAndStock(2L, stockService.getStock(3L)).getQuantity());
		assertEquals(15.0, portfolioService.getPortfolioByUserIdAndStock(1L, stockService.getStock(1L)).getQuantity());
	}

	@Test
	public void testUpdateOrderChangePriceAndQuantitySequential() {
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

}
