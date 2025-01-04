import { BASE_URL } from "@/configs/config.js";
import {getDefaultTradingStock} from "@/services/stockService.js";

const API = `${BASE_URL}/orders`;

const defaultTradingStock = await getDefaultTradingStock();

const fetchOrderById = async (id) => {
    try {
        const response = await fetch(`${API}/${id}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const order = await response.json();

        order.stockId = order.orderType.toUpperCase() === "SELL" ? order.soldStockId : order.boughtStockId;

        order.orderType = order.orderType.toLowerCase();

        return order;

    } catch (error) {
        console.error('Error fetching order:', error);
    }
};

const createOrder = async (orderData) => {

    let order = {
        price: orderData.price,
        quantity: orderData.quantity,
        userId: orderData.userId,
        stockId: orderData.stockId,
        orderType: orderData.orderType
    };

    order = { ...order, ...addBothStocksToOrder(order)};

    try {
        const response = await fetch(API, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(order)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error creating order:', error);
    }
};

const updateOrder = async (order) => {

    order = { ...order, ...addBothStocksToOrder(order)};

    try {
        const response = await fetch(API, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(order)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error updating order:', error);
    }
};

const deleteOrder = async (order) => {
    try {
        order.orderType = order.orderType.toUpperCase();
        const response = await fetch(API, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(order)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error deleting order:', error);
    }
};

const addBothStocksToOrder = orderData => {

    const defaultTradingStockId = defaultTradingStock.id;

    let order = {}

    if (orderData.orderType.toUpperCase() === 'BUY') {
        order.boughtStockId = orderData.stockId;
        order.soldStockId = defaultTradingStockId;
    } else if (orderData.orderType.toUpperCase() === 'SELL') {
        order.soldStockId = orderData.stockId;
        order.boughtStockId = defaultTradingStockId;
    } else {
        throw new Error("Unsupported operation");
    }

    order.orderType = orderData.orderType.toUpperCase();

    return order;
};

const fetchAllOrders = async () => {
    try {
        const response = await fetch(`${API}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const orders = await response.json();

        orders.forEach(order => {
            order.stockId = order.orderType.toUpperCase() === "SELL" ? order.soldStockId : order.boughtStockId;
            order.orderType = order.orderType.toLowerCase();
        })

        console.log(orders)

        return orders;
    } catch (error) {
        console.error('Error fetching orders:', error);
    }
};



export {
    createOrder,
    deleteOrder,
    updateOrder,
    fetchOrderById,
    fetchAllOrders
};
