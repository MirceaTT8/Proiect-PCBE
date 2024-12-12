import { BASE_URL } from "@/configs/config.js";

const API = `${BASE_URL}/stocks`;

const fetchStockById = async (id) => {
    try {
        console.log(`${API}/${id}`)

        const response = await fetch(`${API}/${id}`);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('Failed to fetch stock. ' + response.statusText);
        }
    } catch (error) {
        console.error('fetchStockById:', error);
        throw error;
    }
};

const createStock = async (stock) => {
    try {
        const response = await fetch(API, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(stock)
        });
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('Failed to create stock. ' + response.statusText);
        }
    } catch (error) {
        console.error('createStock:', error);
        throw error;
    }
};

const updateStock = async (stock) => {
    try {
        const response = await fetch(API, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(stock)
        });
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('Failed to update stock. ' + response.statusText);
        }
    } catch (error) {
        console.error('updateStock:', error);
        throw error;
    }
};

const deleteStock = async (id) => {
    try {
        const response = await fetch(`${API}/${id}`, {
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error('Failed to delete stock. ' + response.statusText);
        }
    } catch (error) {
        console.error('deleteStock:', error);
        throw error;
    }
};

export {
    createStock,
    deleteStock,
    updateStock,
    fetchStockById,
};