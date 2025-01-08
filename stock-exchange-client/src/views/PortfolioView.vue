<template>
  <div class="container">
    <!-- Existing Portfolio Section -->
    <div class="portfolios">
      <PortfolioList
          v-if="loadedPortfolios"
          :portfolios="portfolios"
          @portfolio-selected="handlePortfolioSelected"
          @delete-portfolio="handleDeletePortfolio"
          @update-portfolio="handleUpdatePortfolio"
      />
    </div>

    <!-- Create Portfolio Form -->
    <div v-if="authenticated" class="create-portfolio">
      <h3>Create New Portfolio</h3>
      <form @submit.prevent="handleCreatePortfolio">
        <label>
          Select Stock:
          <select v-model="newPortfolio.stockId" required>
            <option v-for="stock in stocks" :key="stock.id" :value="stock.id">
              {{ stock.name }}
            </option>
          </select>
        </label>
        <button type="submit">Create Portfolio</button>
      </form>
    </div>

    <!-- Order Placer Section -->
    <div v-if="selectedPortfolio" class="orders">
      <OrderPlacer :stock="selectedPortfolio.stock" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { fetchStocks } from "@/services/stockService.js";
import PortfolioList from "@/components/PortfolioList.vue";
import { fetchPortfolios, createPortfolio, deletePortfolio, updatePortfolio } from "@/services/portfolioService.js";
import { getCurrentUser, isAuthenticated } from "@/services/userService.js";
import OrderPlacer from "@/components/OrderPlacer.vue";

const stocks = ref([]); // Array to hold stocks
const authenticated = isAuthenticated(); // Check if user is authenticated

// Fetch stocks from the API
const fetchStocksList = async () => {
  try {
    if (!authenticated) {
      console.warn("User not authenticated, cannot fetch stocks.");
      return;
    }
    const response = await fetchStocks(); // Fetch all stocks
    stocks.value = response; // Update the stocks array
  } catch (error) {
    console.error("Error fetching stocks:", error);
  }
};

onMounted(async () => {
  if (authenticated) {
    await fetchStocksList(); // Fetch stocks on mount if authenticated
  }
});

const portfolios = ref([]);
const selectedPortfolio = ref();
const loadedPortfolios = ref(false);
const newPortfolio = ref({ stockId: "", quantity: 0 });

const attachStockData = async (portfolios) => {
  if (authenticated) {
    const fetchedStocks = await fetchStocks();
    portfolios.forEach((portfolio) => {
      portfolio.stock = fetchedStocks.find(stock => stock.id === portfolio.stockId);
    });
  }
  return portfolios;
};

onMounted(async () => {
  const currentUser = getCurrentUser();
  if (currentUser && currentUser.id) {
    const fetchedPortfolios = await fetchPortfolios(currentUser.id);
    portfolios.value = await attachStockData(fetchedPortfolios);

    if (portfolios.value.length > 0) {
      selectedPortfolio.value = portfolios.value[0];
    } else {
      selectedPortfolio.value = null;
    }
  }
  loadedPortfolios.value = true;
});

const handlePortfolioSelected = (portfolio) => {
  selectedPortfolio.value = portfolio;
};

const handleCreatePortfolio = async () => {
  try {
    const currentUser = getCurrentUser();
    if (!currentUser || !currentUser.id) {
      throw new Error("User not authenticated");
    }

    // Create a new portfolio
    const createdPortfolio = await createPortfolio({
      userId: currentUser.id,
      stockId: newPortfolio.value.stockId,
      quantity: newPortfolio.value.quantity, // User-specified quantity
    });

    if (createdPortfolio) {
      // Fetch the latest stocks to ensure data is fresh
      await fetchStocksList();

      // Attach stock data dynamically
      const stock = stocks.value.find(stock => stock.id === createdPortfolio.stockId);
      if (stock) {
        createdPortfolio.stock = stock; // Attach the stock details
        createdPortfolio.evaluation = stock.price * createdPortfolio.quantity; // Calculate evaluation
      } else {
        createdPortfolio.evaluation = 0; // Fallback if stock is not found
      }

      // Add the updated portfolio to the list
      portfolios.value.push(createdPortfolio);

      // Reset the newPortfolio form
      newPortfolio.value = { stockId: "", quantity: 0 };

      // Set the newly created portfolio as the selected one
      selectedPortfolio.value = createdPortfolio;

      console.log("Created Portfolio:", createdPortfolio); // Debug log
    }
  } catch (error) {
    console.error("Failed to create portfolio:", error);
  }
};


const handleDeletePortfolio = async (portfolioId) => {
  try {
    await deletePortfolio(portfolioId);
    portfolios.value = portfolios.value.filter(p => p.id !== portfolioId);
    if (selectedPortfolio.value?.id === portfolioId) {
      selectedPortfolio.value = portfolios.value.length > 0 ? portfolios.value[0] : null;
    }
  } catch (error) {
    console.error("Failed to delete portfolio:", error);
  }
};

const handleUpdatePortfolio = async (updatedPortfolio) => {
  try {
    const result = await updatePortfolio(updatedPortfolio);
    const index = portfolios.value.findIndex(p => p.id === result.id);
    if (index !== -1) {
      portfolios.value[index] = result;
      if (selectedPortfolio.value?.id === result.id) {
        selectedPortfolio.value = result;
      }
    }
  } catch (error) {
    console.error("Failed to update portfolio:", error);
  }
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: flex-start;
  justify-content: space-around;
}

.create-portfolio {
  margin: 20px;
}

.create-portfolio form {
  display: flex;
  flex-direction: column;
}

.create-portfolio label {
  margin-bottom: 10px;
}

.create-portfolio select {
  padding: 5px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.create-portfolio input {
  padding: 5px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.create-portfolio button {
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.create-portfolio button:hover {
  background-color: #0056b3;
}
</style>
