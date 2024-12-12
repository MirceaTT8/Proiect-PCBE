import {BASE_URL} from "@/configs/config.js";

const API = `${BASE_URL}/stocks`;

const fetchStocks = async () => {
    try {
        const data = await fetch(API);
        const stocks = await data.json();
        console.log(stocks);
        return stocks.filter(stock => !stock.symbol.startsWith("$"));
    } catch (error) {
        console.error(error);
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
    getDefaultTradingStock,
}