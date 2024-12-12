import { BASE_URL } from "@/configs/config.js";

const API = `${BASE_URL}/portfolios`;

const fetchPortfolioById = async (id) => {
    try {
        const response = await fetch(`${API}/${id}`, { method: 'GET' });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Fetching portfolio failed:', error);
    }
};

const createPortfolio = async (portfolio) => {
    try {
        const response = await fetch(API, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(portfolio)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Creating portfolio failed:', error);
    }
};

const updatePortfolio = async (portfolio) => {
    try {
        const response = await fetch(API, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(portfolio)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Updating portfolio failed:', error);
    }
};

const deletePortfolio = async (id) => {
    try {
        const response = await fetch(`${API}/${id}`, { method: 'DELETE' });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Deleting portfolio failed:', error);
    }
};

export {
    createPortfolio,
    deletePortfolio,
    updatePortfolio,
    fetchPortfolioById,
};