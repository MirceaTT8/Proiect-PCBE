<template>

    <LoginComponent />
    
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
</template>

<script>
import { onMounted, ref } from "vue";
import { fetchStocks } from "@/services/stockService.js";

import OrderPlacer from "@/components/OrderPlacer.vue";
import StockList from "@/components/StockList.vue";
import StockPriceChart from "@/components/StockPriceChart.vue";
import LoginComponent from "@/components/LoginComponent.vue";

export default {
  components: {
    StockPriceChart,
    StockList,
    OrderPlacer,
    LoginComponent,
  },
  setup() {
    const popupTrigger = ref(true);

    const togglePopup = (trigger) => {
      popupTrigger.value = !popupTrigger.value
    }

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
      popupTrigger,
      togglePopup
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
  margin-left: 70px;
}

.order-placer-column {
  flex: 1;
  padding-left: 20px;
}



.modal-overlay {
  position: relative;
}

.modal-content {
  position: absolute;
  top: 0;
  left: 0;
  background-color: rgba(0,0,0,0.1);
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  
}

.modal-content > div {
    background-color: white;
    padding: 50px;
    border-radius: 10px;
}
</style>