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
import { BASE_URL } from "@/configs/config.js";
import {onMounted, onBeforeUnmount, ref} from "vue";
import {getCurrentUser} from "@/services/userService.js";
import {fetchStocks, getDefaultTradingStock} from "@/services/stockService.js";
import OrderPlacer from "@/components/OrderPlacer.vue";
import OrderList from "@/components/OrderList.vue";
import {fetchOrdersByUserId} from "@/services/orderService.js";

const orders = ref([]);
const selectedOrder = ref();

const loadedOrders = ref(false);

const eventSource = new EventSource(`${BASE_URL}/subscribe`);

const attachStockData = async (orders) => {
  const stocks = await fetchStocks();
  orders.forEach((order) => {
    order.stock = stocks.find(stock => stock.id === order.stockId);
  })
  return orders;
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
  const fetchedOrders = await fetchOrdersByUserId(getCurrentUser().id);

  orders.value = await attachStockData(fetchedOrders);

  if (orders.value.length > 0) {
    selectedOrder.value = orders.value[0];
  } else {
    selectedOrder.value = null;
  }

  loadedOrders.value = true;
}

onMounted(async () => {
  startListening();
  const fetchedOrders = await fetchOrdersByUserId(getCurrentUser().id);

  orders.value = await attachStockData(fetchedOrders);

  if (orders.value.length > 0) {
    selectedOrder.value = orders.value[0];
  } else {
    selectedOrder.value = null;
  }

  loadedOrders.value = true;
});

onBeforeUnmount(async () => {
  stopListening();
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