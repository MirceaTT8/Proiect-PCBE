import {BASE_URL} from "@/configs/config.js";
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
const fetchDefaultTradingStock = async () => {
    const data = await fetch(API);
    const stocks = await data.json();

    const stock = stocks.find(stock => stock.symbol.startsWith("$"));
    console.log(stock);
    return stock;
}

const setDefaultTradingStock = (stock) => {
    localStorage.setItem("defaultStock", JSON.stringify(stock));
}

const getDefaultTradingStock = () => {
    return JSON.parse(localStorage.getItem("defaultStock"));
}

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

export {
    fetchStocks,
    fetchDefaultTradingStock,
    getDefaultTradingStock,
    setDefaultTradingStock
}