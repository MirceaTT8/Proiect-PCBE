<template>
  <h1>This is PortfolioView</h1>
  <PortfolioList v-if="loadedPortfolios"
                 :portfolios="portfolios"
                 @portfolio-selected="handlePortfolioSelected"
  />
</template>

<script setup>
import {onMounted, ref} from "vue";

import PortfolioList from "@/components/PortfolioList.vue";
import {fetchPortfolios} from "@/services/portfolioService.js";
import {getCurrentUser} from "@/services/userService.js";
import {fetchStocks, getDefaultTradingStock} from "@/services/stockService.js";

const portfolios = ref([]);
const selectedPortfolio = ref();

const loadedPortfolios = ref(false);

const attachStockData = async (portfolios) => {
  const stocks = await fetchStocks();
  portfolios.forEach((portfolio) => {
    if (portfolio.stockId === getDefaultTradingStock().id) {
      portfolio.stock = getDefaultTradingStock();
    } else {
      portfolio.stock = stocks.find(stock => stock.id === portfolio.stockId);
    }
  });
  return portfolios;
};

onMounted(async () => {


  const fetchedPortfolios = await fetchPortfolios(getCurrentUser().id);

  portfolios.value = await attachStockData(fetchedPortfolios);

  if (portfolios.value.length > 0) {
    selectedPortfolio.value = portfolios.value[0];
  } else {
    selectedPortfolio.value = null;
  }

  loadedPortfolios.value = true;
});

const handlePortfolioSelected = (portfolio) => {
  selectedPortfolio.value = portfolio;
}

</script>

<style>

</style>