<template>
  <div class="container">
    <div class="portfolios">
      <PortfolioList
          v-if="loadedPortfolios"
          :portfolios="portfolios"
          @portfolio-selected="handlePortfolioSelected"
      />
    </div>
    <div class="create-portfolio">
      <h3>Create New Portfolio</h3>
      <form @submit.prevent="handleCreatePortfolio">
        <label>
          Stock ID:
          <input v-model="newPortfolio.stockId" type="text" required />
        </label>
        <label>
          Quantity:
          <input v-model="newPortfolio.quantity" type="number" required />
        </label>
        <button type="submit">Create Portfolio</button>
      </form>
    </div>
    <div class="orders">
      <OrderPlacer
          v-if="selectedPortfolio"
          :stock="selectedPortfolio.stock"
      />
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";

import PortfolioList from "@/components/PortfolioList.vue";
import { fetchPortfolios, createPortfolio } from "@/services/portfolioService.js";
import { getCurrentUser, isAuthenticated } from "@/services/userService.js";
import { fetchStocks, getDefaultTradingStock } from "@/services/stockService.js";
import OrderPlacer from "@/components/OrderPlacer.vue";

const portfolios = ref([]);
const selectedPortfolio = ref();
const loadedPortfolios = ref(false);
const authenticated = isAuthenticated();
const newPortfolio = ref({ stockId: "", quantity: 0 });

const attachStockData = async (portfolios) => {
  if (authenticated) {
    const stocks = await fetchStocks();
    portfolios.forEach((portfolio) => {
      if (portfolio.stockId === getDefaultTradingStock().id) {
        portfolio.stock = getDefaultTradingStock();
      } else {
        portfolio.stock = stocks.find(stock => stock.id === portfolio.stockId);
      }
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

    const createdPortfolio = await createPortfolio({
      userId: currentUser.id,
      stockId: newPortfolio.value.stockId,
      quantity: newPortfolio.value.quantity,
    });

    if (createdPortfolio) {
      // Update the portfolio list dynamically
      portfolios.value.push(createdPortfolio);
      selectedPortfolio.value = createdPortfolio;
      newPortfolio.value = { stockId: "", quantity: 0 }; // Reset form
    }
  } catch (error) {
    console.error("Failed to create portfolio:", error);
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

.create-portfolio input {
  padding: 5px;
  margin-bottom: 10px;
}

.create-portfolio button {
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.create-portfolio button:hover {
  background-color: #0056b3;
}
</style>
