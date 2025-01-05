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
</template>

<script>
import { onMounted, onBeforeUnmount, ref } from "vue";
import { fetchStocks } from "@/services/stockService.js";
import { BASE_URL } from "@/configs/config.js";
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
      fetchFilteredStocks();
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
        } else {
          selectedStock.value = null;
        }
      } catch (error) {
        console.error("Error fetching stocks:", error);
      }
    };

    // Fetch all stocks on component mount
    onMounted(() => {
      startListening();
      fetchFilteredStocks();
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
    };

    return {
      stocks,
      selectedStock,
      searchQuery,
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
</style>
