import {BASE_URL} from "@/configs/config.js";
import {getCurrentUser} from "@/services/userService.js";
import { getDefaultTradingStock } from "./stockService";

const API = `${BASE_URL}/orders`;

const placeOrder = async (orderData) => {

    const defaultTradingStockId = getDefaultTradingStock().id;

    const order = {
        price: orderData.price,
        quantity: orderData.quantity,
        userId: getCurrentUser().id,
    };

    if (orderData.orderType === 'Buy') {
        order.boughtStockId = orderData.stockId;
        order.soldStockId = defaultTradingStockId;
        order.orderType = "BUY"
    } else if (orderData.orderType === 'Sell') {
        order.soldStockId = orderData.stockId;
        order.boughtStockId = defaultTradingStockId;
        order.orderType = "SELL"
    } else {
        throw new Error("Unsupported operation");
    }

    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'cors': 'no-cors',
        },
        body: JSON.stringify(order)
    };

    try {
        const response = await fetch(API, options);
        if (!response.ok) {
            console.error(response.statusText);
            throw new Error(response.statusText);
        }
        const data = await response.json();
        console.log('Order placed successfully:', data);
        return data;
    } catch (error) {
        console.error('Error placing order:', error);
        throw new Error('Error placing order');
    }
};

const updateOrder = async (order) => {

    order = {
        ...order,
        ...addBothStocksToOrder(order),
        userId: getCurrentUser().id
    };


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

const fetchOrdersByUserId = async (userId) => {
    try {
        const response = await fetch(`${API}/user/${userId}`);
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
        console.error('Error fetching order:', error);
    }
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

        console.log(orders);

        return orders;
    } catch (error) {
        console.error('Error fetching orders:', error);
    }
};

const cancelOrder = async (order) => {

    order = {
        ...order,
        ...addBothStocksToOrder(order),
        userId: getCurrentUser().id
    };

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
    } catch (error) {
        console.error('Error deleting order:', error);
    }
};

const addBothStocksToOrder = orderData => {

    const defaultTradingStockId = getDefaultTradingStock().id;

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

export {
    placeOrder,
    fetchOrdersByUserId,
    updateOrder,
    cancelOrder,
    fetchAllOrders
}