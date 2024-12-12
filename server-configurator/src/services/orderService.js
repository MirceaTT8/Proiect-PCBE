import { BASE_URL } from "@/configs/config.js";

const API = `${BASE_URL}/orders`;

const fetchOrderById = async (id) => {
    try {
        const response = await fetch(`${API}/${id}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching order:', error);
    }
};

const createOrder = async (order) => {
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

const deleteOrder = async (id) => {
    try {
        const response = await fetch(`${API}/${id}`, {
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error deleting order:', error);
    }
};

export {
    createOrder,
    deleteOrder,
    updateOrder,
    fetchOrderById,
};
