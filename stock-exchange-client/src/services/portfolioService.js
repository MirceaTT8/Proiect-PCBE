import {BASE_URL} from "@/configs/config.js";

const API = `${BASE_URL}/portfolios`;

const fetchPortfolios = async (userId) => {
    try {
        const response = await fetch(`${API}?userId=${userId}`,
            {method: 'GET'});

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Fetching portfolio failed:', error);
    }
};

export {
    fetchPortfolios,
}