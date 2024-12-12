import {BASE_URL} from "@/configs/config.js";

const API = `${BASE_URL}/stock-history`;

const fetchStockHistory = async (stockId, range) => {
    let url = `${API}/${stockId}`;
    if (range !== 'all') {
        url += `?days=${range}`;
    }
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Failed to fetch stock history data');
        }
        const data = await response.json();
        console.log('Update successful:', data);
        return data;
    } catch (error) {
        console.error('Error fetching stock history data:', error);
        return [];
    }
};

export {
    fetchStockHistory,
}