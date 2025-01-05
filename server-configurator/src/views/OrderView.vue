<template>
  <div class="order-management-container">
    <div class="order-management-form">
      <h1>Order Management</h1>
      <form @submit.prevent="">
        <div class="form-group">
          <label for="id">Order ID</label>
          <input
              id="id"
              v-model="order.id"
              type="text"
              placeholder="Enter order ID to load data or leave empty to create new order"
          />
        </div>
        <div class="form-group">
          <label for="userId">User ID</label>
          <input id="userId" v-model="order.userId" type="text" required />
        </div>
        <div class="form-group">
          <label for="stockId">Stock ID</label>
          <input id="stockId" v-model="order.stockId" type="text" required />
        </div>
        <div class="form-group">
          <label for="quantity">Quantity</label>
          <input
              id="quantity"
              v-model.number="order.quantity"
              type="number"
              min="1"
              required
          />
        </div>
        <div class="form-group">
          <label for="price">Price</label>
          <input
              id="price"
              v-model.number="order.price"
              type="number"
              step="0.01"
              min="0"
              required
          />
        </div>
        <div class="form-group">
          <label for="orderType">Order Type</label>
          <select id="orderType" v-model="order.orderType" required>
            <option disabled value="">Select Order Type</option>
            <option value="buy">Buy</option>
            <option value="sell">Sell</option>
          </select>
        </div>
        <div class="button-group">
          <button
              v-if="!order.id"
              type="button"
              class="create-button"
              @click="handleCreate"
              :disabled="isSubmitting"
          >
            Create
          </button>
          <div v-else>
            <button
                type="button"
                class="update-button"
                @click="handleUpdate"
                :disabled="isSubmitting"
            >
              Update
            </button>
            <button
                type="button"
                class="delete-button"
                @click="handleDelete"
                :disabled="isSubmitting"
            >
              Delete
            </button>
          </div>
        </div>
      </form>
      
    </div>
    
    <Notification v-if="message" :message="message" :type="messageType" />
    <div class="order-list">
        <OrderList v-if="loadedOrders"
                 :orders="orders"       
        />
      </div>
  </div>
</template>

<script setup>
import { BASE_URL } from "@/configs/config.js";
import {createCommentVNode, onBeforeUnmount, onMounted, reactive, ref, watch} from 'vue';
import OrderList from "@/components/OrderList.vue";
import {fetchStocks, getDefaultTradingStock} from "@/services/stockService.js";
import {
  fetchOrderById,
  createOrder,
  updateOrder,
  deleteOrder,
  fetchAllOrders
} from '@/services/orderService.js';
import Notification from '@/components/Notification.vue';

const order = reactive({
  id: '',
  userId: '',
  stockId: '',
  quantity: 1,
  price: 0.0,
  orderType: '',
});

const orders = ref([]);
const loadedOrders = ref(false);
const attachStockData = async (orders) => {
  const stocks = await fetchStocks();
  orders.forEach((order) => {
    order.stock = stocks.find(stock => stock.id === order.stockId);
  })
  return orders;
};

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
  if (this.eventSource) {
    this.eventSource.close(); // Close the connection
    console.log('Stopped listening to SSE.');
  }
}

const updateUI = async (data) => {
  const fetchedOrders = await fetchAllOrders();
  orders.value = await attachStockData(fetchedOrders);
  loadedOrders.value = true;
}

onMounted(async () => {
  
  startListening();

  const fetchedOrders = await fetchAllOrders();

  orders.value = await attachStockData(fetchedOrders);

  loadedOrders.value = true;
});

onBeforeUnmount(async () => {
  stopListening();
});


const isSubmitting = ref(false);
const message = ref('');
const messageType = ref('');

const resetForm = () => {
  order.userId = '';
  order.stockId = '';
  order.quantity = 1;
  order.price = 0.0;
  order.orderType = '';
  order.id = '';
};

const fetchOrderData = async (id) => {
  try {
    if (id) {
      const orderData = await fetchOrderById(id);
      if (orderData) {
        Object.assign(order, orderData);
        message.value = '';

        console.log(orderData);

      } else {
        resetForm();
        message.value = 'Order not found.';
        messageType.value = 'error';
      }
    } else {
      resetForm();
      message.value = '';
    }
  } catch (error) {
    resetForm();
    message.value = `Error fetching order: ${error.message}`;
    messageType.value = 'error';
  }
};

watch(() => order.id, (newId) => {
  if (newId) {
    fetchOrderData(newId);
  } else {
    resetForm();
  }
});

const handleCreate = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    const newOrder = { ...order };
    delete newOrder.id;

    if(newOrder.stockId == 3){
      message.value = "EURO stock order not allowed!";
      messageType.value = 'error';
      throw new Error('Invalid trade EURO for EURO');
    }

    const createdOrder = await createOrder(newOrder);
    if (createdOrder) {
      message.value = 'Order created successfully!';
      messageType.value = 'success';
    } else {
      throw new Error('Invalid response from server.');
    }
  } catch (error) {
    message.value = `Error creating order: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};

const handleUpdate = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    if(order.stockId == 3){
      message.value = "EURO stock order not allowed!";
      messageType.value = 'error';
      throw new Error('Invalid trade EURO for EURO');
    }
    await updateOrder(order);
    message.value = 'Order updated successfully!';
    messageType.value = 'success';
  } catch (error) {
    message.value = `Error updating order: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};

const handleDelete = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    await deleteOrder(order);
    message.value = 'Order deleted successfully!';
    messageType.value = 'success';
    resetForm();
  } catch (error) {
    message.value = `Error deleting order: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<style scoped>
.order-management-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
  background-color: #f9f9f9;
  padding: 120px;
  position: relative;
  border-radius: 20px;
  flex-direction: column;
  
}

.order-management-form {
  background-color: #ffffff;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  
}

.order-list {
  margin-top: 20px;
  height: 200px;
}

.order-management-form h1 {
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  color: #333;
}

.form-group {
  margin-bottom: 1rem;
  text-align: left;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
  color: #555;
}

.form-group input,
.form-group select {
  width: 300px;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}

.button-group {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 2rem;
}

.create-button,
.update-button,
.delete-button {
  width: 48%;
  padding: 0.75rem;
  border: none;
  border-radius: 15px;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-top: 1.5rem;
}

.create-button {
  background-color: #28a745;
}

.update-button {
  background-color: #007bff;
}

.delete-button {
  background-color: #dc3545;
}

.create-button:hover {
  background-color: #218838;
}

.update-button:hover {
  background-color: #0056b3;
}

.delete-button:hover {
  background-color: #c82333;
}
</style>