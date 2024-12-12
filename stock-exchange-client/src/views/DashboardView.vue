<template>
  <div class="dashboard">
    <h1>My Dashboard</h1>
    <div class="content">
      <div class="stock-list-column">
        <StockList :stocks="stocks" />
      </div>
      <div class="order-placer-column">
        <OrderPlacer stock-id="0"/>
      </div>
    </div>
  </div>
</template>

<script>
import OrderPlacer from "@/components/OrderPlacer.vue";
import StockList from "@/components/StockList.vue";
import { onMounted, ref } from "vue";
import { fetchStocks } from "@/services/stockService.js";

export default {
  components: {
    StockList,
    OrderPlacer,
  },
  setup() {
    const stocks = ref([]);

    onMounted(async () => {
      stocks.value = await fetchStocks();
    });

    return {
      stocks,
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