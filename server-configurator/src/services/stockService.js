import { BASE_URL } from "@/configs/config.js";
import {fetchStockHistory} from "@/services/stockHistoryService.js";

const API = `${BASE_URL}/stocks`;

const fetchStocks = async (query = "") => {
    try {
        const url = query
            ? `${API}?q=${encodeURIComponent(query)}` // Add query parameter if provided
            : API;
        const response = await fetch(url);
        const stocks = await response.json();

        return addDayBeforePriceToStocks(
            stocks.filter(stock => !stock.symbol.startsWith("$"))
        );
    } catch (error) {
        console.error("Error fetching stocks:", error);
        return [];
    }
};

const addDayBeforePriceToStocks = async (stocks) => {
    try {
        const promises = stocks.map(async (stock) => {
            const stockHistory = await fetchStockHistory(stock.id, 1);
            if (stockHistory && stockHistory.length > 0) {
                stock.priceADayBefore = stockHistory.pop().price;
            } else {
                console.error(`No history found for stock ID: ${stock.id}`);
                stock.priceADayBefore = null;
            }
            return stock;
        });

        const updatedStocks = await Promise.all(promises);
        return updatedStocks;

    } catch (error) {
        console.error("Error fetching stock data:", error);
        return stocks;
    }
}

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

const getDefaultTradingStock = async () => {
    const data = await fetch(API);
    const stocks = await data.json();

    const stock = stocks.find(stock => stock.symbol.startsWith("$"));
    console.log(stock);
    return stock;
}

export {
    fetchStocks,
    createStock,
    deleteStock,
    updateStock,
    fetchStockById,
    getDefaultTradingStock,
};