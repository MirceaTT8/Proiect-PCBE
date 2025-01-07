<template>
  <LoginComponent />

  <h1>My Dashboard</h1>

  <!-- Search Bar with Button -->
  <div class="search-bar">
    <input
        type="text"
        v-model="searchQuery"
        placeholder="Search by stock name or symbol..."
    />
    <button @click="searchStocks">Search</button>
  </div>

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
  <div class="order-lists-container">
    <div class="order-list">
      <h2>Users are buying...</h2>
      <OrderList v-if="loadedOrders"
                :orders="buyOrders"           
      />
    </div>
    <div class="order-list">
      <h2>Users are selling...</h2>
      <OrderList v-if="loadedOrders"
                :orders="sellOrders"           
      />
    </div>
  </div>
</template>

<script>
import { onMounted, onBeforeUnmount, ref } from "vue";
import { fetchStocks } from "@/services/stockService.js";
import { BASE_URL } from "@/configs/config.js";
import OrderList from "@/components/OrderList.vue";
import OrderPlacer from "@/components/OrderPlacer.vue";
import StockList from "@/components/StockList.vue";
import StockPriceChart from "@/components/StockPriceChart.vue";
import LoginComponent from "@/components/LoginComponent.vue";
import { fetchAllOrders } from "@/services/orderService";

export default {
  components: {
    StockPriceChart,
    StockList,
    OrderPlacer,
    OrderList,
    LoginComponent
  },
  setup() {
    const orders = ref([]);
    const buyOrders = ref([]);
    const sellOrders = ref([]);
    const loadedOrders = ref(false);
    const attachStockData = async (orders) => {
      const stocks = await fetchStocks();
      orders.forEach((order) => {
        order.stock = stocks.find(stock => stock.id === order.stockId);
      })
      return orders;
    };

    const stocks = ref([]); // Holds the array of stocks
    const selectedStock = ref(); // Holds the currently selected stock
    const searchQuery = ref(""); // Holds the search query

    const eventSource = new EventSource(`${BASE_URL}/subscribe`);

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
      console.log('UpdateUI se aplica.');

      const DEF_DELAY = 1000;

      function sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms || DEF_DELAY));
      }

      await sleep(100);

      const fetchedOrders = await fetchAllOrders();
      orders.value = await attachStockData(fetchedOrders);
      orders.value = orders.value.sort((a,b) => a.price - b.price);
      
      buyOrders.value = orders.value.filter(order => order.orderType.toUpperCase() === "BUY");
      sellOrders.value = orders.value.filter(order => order.orderType.toUpperCase() === "SELL");

      
      buyOrders.value = buyOrders.value.filter(order => order.boughtStockId === selectedStock.value.id);
      sellOrders.value = sellOrders.value.filter(order => order.soldStockId === selectedStock.value.id);
      

      loadedOrders.value = true;
    }

    // Fetch stocks from the API, optionally filtered by a query
    const fetchFilteredStocks = async (query = "") => {
      try {
        console.log("Fetching stocks with query:", query);
        const response = await fetchStocks(query); // Fetch stocks from the API
        stocks.value = response; // Update the stocks array
        console.log("Stocks fetched:", stocks.value);

        // Set the first stock as selected if results are returned
        if (stocks.value.length > 0) {
          selectedStock.value = stocks.value[0];
          buyOrders.value = orders.value.filter(order => order.orderType.toUpperCase() === "BUY");
          sellOrders.value = orders.value.filter(order => order.orderType.toUpperCase() === "SELL");

          buyOrders.value = buyOrders.value.filter(order => order.boughtStockId === selectedStock.value.id);
          sellOrders.value = sellOrders.value.filter(order => order.soldStockId === selectedStock.value.id);
        } else {
          selectedStock.value = null;
        }
      } catch (error) {
        console.error("Error fetching stocks:", error);
      }
    };

    // Fetch all stocks on component mount
    onMounted(async () => {
      startListening();
      fetchFilteredStocks();
      const fetchedOrders = await fetchAllOrders();
      orders.value = await attachStockData(fetchedOrders);
      orders.value = orders.value.sort((a,b) => a.price - b.price);
      
      buyOrders.value = orders.value.filter(order => order.orderType.toUpperCase() === "BUY");
      sellOrders.value = orders.value.filter(order => order.orderType.toUpperCase() === "SELL");

      
      
      loadedOrders.value = true;
    });

    onBeforeUnmount(async () => {
      stopListening();
    });

    // Search stocks when the search button is clicked
    const searchStocks = () => {
      console.log("Searching stocks with query:", searchQuery.value);
      fetchFilteredStocks(searchQuery.value); // Fetch filtered stocks
    };

    // Update selected stock when a stock is clicked
    const handleStockSelected = (stock) => {
      console.log("Selected Stock:", stock);
      selectedStock.value = stock;

      buyOrders.value = orders.value.filter(order => order.orderType.toUpperCase() === "BUY");
      sellOrders.value = orders.value.filter(order => order.orderType.toUpperCase() === "SELL");

      buyOrders.value = buyOrders.value.filter(order => order.boughtStockId === selectedStock.value.id);
      sellOrders.value = sellOrders.value.filter(order => order.soldStockId === selectedStock.value.id);
    };

    return {
      orders,
      buyOrders,
      sellOrders,
      loadedOrders,
      stocks,
      selectedStock,
      searchQuery,
      attachStockData,
      startListening,
      stopListening,
      updateUI,
      searchStocks,
      handleStockSelected,
    };
  },
};
</script>

<style>
.dashboard {
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
}

.search-bar {
  margin: 20px;
  text-align: center;
}

.search-bar input {
  width: 50%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-right: 10px;
}

.search-bar button {
  padding: 10px 15px;
  font-size: 16px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.search-bar button:hover {
  background-color: #0056b3;
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

.order-lists-container {
  display: flex;
  flex-direction: row;
}
</style>
