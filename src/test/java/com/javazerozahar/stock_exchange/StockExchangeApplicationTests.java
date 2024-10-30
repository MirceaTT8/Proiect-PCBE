package com.javazerozahar.stock_exchange;

import com.javazerozahar.stock_exchange.model.dto.OrderDTO;
import com.javazerozahar.stock_exchange.model.dto.OrderType;
import com.javazerozahar.stock_exchange.service.OrderService;
import com.javazerozahar.stock_exchange.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockExchangeApplicationTests {

	private OrderService orderService;

	@BeforeEach
	public void prepare() {
		StockExchangeApplication.main(null);
		this.orderService = SingletonFactory.getInstance(OrderService.class);
	}

	@Test
	void testAddTwoOrdersBuyOneSequential() {

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
		orderService.placeOrder(order3, "create");

	}

}
