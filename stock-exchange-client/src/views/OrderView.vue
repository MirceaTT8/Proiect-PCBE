<template>
  <h1>My Open Orders</h1>
  <div class="container">
    <div class="orders">
      <OrderList v-if="loadedOrders"
                 :orders="orders"
                 @order-selected="handleOrderSelected"
      />
    </div>
    <div class="order-placer">
      <OrderPlacer v-if="selectedOrder"
                   :stock="selectedOrder.stock"
      />
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {getCurrentUser} from "@/services/userService.js";
import {fetchStocks, getDefaultTradingStock} from "@/services/stockService.js";
import OrderPlacer from "@/components/OrderPlacer.vue";
import OrderList from "@/components/OrderList.vue";
import {fetchOrdersByUserId} from "@/services/orderService.js";

const orders = ref([]);
const selectedOrder = ref();

const loadedOrders = ref(false);

const attachStockData = async (orders) => {
  const stocks = await fetchStocks();
  orders.forEach((order) => {
    order.stock = stocks.find(stock => stock.id === order.stockId);
  })
  return orders;
};

onMounted(async () => {
  const fetchedOrders = await fetchOrdersByUserId(getCurrentUser().id);

  orders.value = await attachStockData(fetchedOrders);

  if (orders.value.length > 0) {
    selectedOrder.value = orders.value[0];
  } else {
    selectedOrder.value = null;
  }

  loadedOrders.value = true;
});

const handleOrderSelected = (portfolio) => {
  selectedOrder.value = portfolio;
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