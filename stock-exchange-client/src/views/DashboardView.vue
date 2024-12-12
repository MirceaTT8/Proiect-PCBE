<template>
  <div class="dashboard">
    <h1>My Dashboard</h1>
    <div class="content">
      <div class="stock-list-column">
        <StockList
            :stocks="stocks"
            @stockSelected="handleStockSelected"
        />
      </div>
      <div class="order-placer-column">
        <StockPriceChart :stock="selectedStock" />
        <OrderPlacer :stock="selectedStock" />
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from "vue";
import { fetchStocks } from "@/services/stockService.js";

import OrderPlacer from "@/components/OrderPlacer.vue";
import StockList from "@/components/StockList.vue";
import StockPriceChart from "@/components/StockPriceChart.vue";

export default {
  components: {
    StockPriceChart,
    StockList,
    OrderPlacer,
  },
  setup() {
    const stocks = ref([]);

    const selectedStock = ref();

    onMounted(async () => {
      console.log("MOUNTED");

      stocks.value = await fetchStocks();

      console.log(stocks.value);

      if (stocks.value.length > 0) {
        selectedStock.value = stocks.value[0];
      } else {
        selectedStock.value = null;
      }
    });

    const handleStockSelected = (stock) => {
      console.log("Selected Stock", stock);
      selectedStock.value = stock;
    }

    return {
      stocks,
      selectedStock,
      handleStockSelected,
    }
  },
}
</script>

<style>
.dashboard {
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
}

.content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex: 1;
}

.stock-list-column {
  flex: 0 0 33%;
  overflow-y: auto;
}

.order-placer-column {
  flex: 1;
  padding-left: 20px;
}
</style>