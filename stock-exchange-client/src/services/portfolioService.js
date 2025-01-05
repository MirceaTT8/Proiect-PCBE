import { BASE_URL } from "@/configs/config.js";

const API = `${BASE_URL}/portfolios`;

// Fetch portfolios by user ID
const fetchPortfolios = async (userId) => {
    try {
        const response = await fetch(`${API}?userId=${userId}`, {
            method: 'GET',
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Fetching portfolio failed:', error);
    }
};

// Create a new portfolio
const createPortfolio = async (portfolioDTO) => {
    try {
        const response = await fetch(API, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(portfolioDTO),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Creating portfolio failed:', error);
    }
};

// Update an existing portfolio
const updatePortfolio = async (portfolioDTO) => {
    try {
        const response = await fetch(API, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(portfolioDTO),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Updating portfolio failed:', error);
    }
};

// Delete a portfolio by ID
const deletePortfolio = async (portfolioId) => {
    try {
        const response = await fetch(`${API}/${portfolioId}`, {
            method: 'DELETE',
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        console.log(`Portfolio ${portfolioId} deleted successfully.`);
    } catch (error) {
        console.error(`Deleting portfolio failed: ${error}`);
    }
};

export {
    fetchPortfolios,
    createPortfolio,
    updatePortfolio,
    deletePortfolio,
};
