<template>
  <LoginComponent />

  <div class="dashboard-container">
    <!-- Left Section -->
    <div class="left-section">
      <div class="search-bar">
        <input
            type="text"
            v-model="searchQuery"
            placeholder="Search by stock name or symbol..."
        />
        <button @click="searchStocks">Search</button>
      </div>

      <div class="main-content">
        <!-- Stock List -->
        <div class="stock-list-column">
          <StockList
              :stocks="stocks"
              @stockSelected="handleStockSelected"
          />
        </div>

        <!-- Order Placer -->
        <div class="order-placer-column">
          <StockPriceChart :stock="selectedStock" />
          <OrderPlacer :stock="selectedStock" />
        </div>
      </div>
    </div>

    <!-- Right Section: Order Lists -->
    <div class="right-section">
      <div v-if="buyOrders.length > 0" class="order-list">
        <h2>Users are buying...</h2>
        <OrderList :orders="buyOrders" />
      </div>
      <div v-if="sellOrders.length > 0" class="order-list">
        <h2>Users are selling...</h2>
        <OrderList :orders="sellOrders" />
      </div>
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

    eventSource.addEventListener('DATA_UPDATE', async (event) => {
        const data = JSON.parse(event.data);
        console.log('Data update:', data);
        await updateUI(data);
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
      await fetchFilteredStocks();
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
<style scoped>
.dashboard-container {
  display: flex;
  flex-direction: row;
  height: 100vh;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

/* Left Section */
.left-section {
  flex: 2;
  display: flex;
  flex-direction: column;
  margin-right: 20px;
}

.search-bar {
  margin: 20px 0;
  display: flex;
  gap: 10px;
  align-items: center;
}

.search-bar input {
  height: 30px;
  width: 50%;
  padding: 5px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.search-bar button {
  height: 30px;
  padding: 5px 10px;
  font-size: 14px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.search-bar button:hover {
  background-color: #0056b3;
}

.main-content {
  display: flex;
  flex-direction: column;
  flex: 1;
}

.stock-list-column {
  flex: 1;
  margin-bottom: 20px;
  overflow-y: auto;
}

.order-placer-column {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}

/* Right Section: Order Lists */
.right-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
  max-width: 400px;
}

.order-list {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
  overflow-y: auto;
}

.order-list h2 {
  margin-bottom: 10px;
  font-size: 18px;
  font-weight: bold;
  color: #333;
}
</style>
