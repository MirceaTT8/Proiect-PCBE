<template>
  <h1>This is PortfolioView</h1>
  <div class="container">
    <div class="portfolios">
      <PortfolioList v-if="loadedPortfolios"
                     :portfolios="portfolios"
                     @portfolio-selected="handlePortfolioSelected"
      />
    </div>
    <div class="orders">
      <OrderPlacer v-if="selectedPortfolio"
                   :stock="selectedPortfolio.stock"
      />
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref, onBeforeUnmount} from "vue";

import PortfolioList from "@/components/PortfolioList.vue";
import {fetchPortfolios} from "@/services/portfolioService.js";
import {getCurrentUser, isAuthenticated} from "@/services/userService.js";
import {fetchStocks, getDefaultTradingStock} from "@/services/stockService.js";
import OrderPlacer from "@/components/OrderPlacer.vue";
import { BASE_URL } from "@/configs/config.js";

const eventSource = new EventSource(`${BASE_URL}/subscribe`);

const portfolios = ref([]);
const selectedPortfolio = ref();

const loadedPortfolios = ref(false);

const authenticated = isAuthenticated();

const attachStockData = async (portfolios) => {
  if(authenticated) {
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

const startListening = () => {

eventSource.onmessage = (event) => {
    console.log('Received message:', event.data);
};

eventSource.addEventListener('DATA_UPDATE', (event) => {
    const data = JSON.parse(event.data);
    console.log('Data update:', data);
    updateUI(data);
});

eventSource.addEventListener('INIT', (event) => {
    console.log('Connected to stream:', event.data);
});

eventSource.onerror = (error) => {
    console.error('SSE error:', error);
    eventSource.close();
};
}

const stopListening = () => {
if (eventSource) {
  eventSource.close(); // Close the connection
  console.log('Stopped listening to SSE.');
}
}

const updateUI = async (data) => {
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
}

onMounted(async () => {
  startListening();
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

onBeforeUnmount(async () => {
  stopListening();
});

const handlePortfolioSelected = (portfolio) => {
  selectedPortfolio.value = portfolio;
}

</script>

<style scoped>
.container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-around;
}
</style>