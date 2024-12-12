<template>
  <div class="stock-management-container">
    <div class="stock-management-form">
      <h1>Stock Management</h1>
      <form @submit.prevent="">
        <div class="form-group">
          <label for="id">Stock ID</label>
          <input
              id="id"
              v-model="stock.id"
              type="text"
              placeholder="Enter stock ID to load data or leave empty to create new stock"
          />
        </div>
        <div class="form-group">
          <label for="symbol">Symbol</label>
          <input id="symbol" v-model="stock.symbol" type="text" required />
        </div>
        <div class="form-group">
          <label for="name">Name</label>
          <input id="name" v-model="stock.name" type="text" required />
        </div>
        <div class="form-group">
          <label for="price">Price</label>
          <input id="price" v-model.number="stock.price" type="number" step="0.01" required />
        </div>
        <div class="button-group">
          <button
              v-if="!stock.id"
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
    <Notification
        v-if="message"
        :message="message"
        :type="messageType"
    />
  </div>
</template>

<script setup>
import { reactive, ref, watch } from 'vue';
import { fetchStockById, createStock, updateStock, deleteStock } from '@/services/stockService.js';
import Notification from '@/components/Notification.vue';

// Reactive stock object including the id field
const stock = reactive({
  id: '',
  symbol: '',
  name: '',
  price: 0.0,
});

// State variables
const isSubmitting = ref(false);
const message = ref('');
const messageType = ref('');

// Reset form fields
const resetForm = () => {
  stock.symbol = '';
  stock.name = '';
  stock.price = 0.0;
  stock.id = '';
};

// Fetch stock data when id changes
const fetchStockData = async (id) => {
  try {
    if (id) {
      const stockData = await fetchStockById(id);
      if (stockData) {
        Object.assign(stock, stockData);
        message.value = '';
      } else {
        resetForm();
        message.value = 'Stock not found.';
        messageType.value = 'error';
      }
    } else {
      resetForm();
      message.value = '';
    }
  } catch (error) {
    resetForm();
    message.value = `Error fetching stock: ${error.message}`;
    messageType.value = 'error';
  }
};

// Watch for changes in stock.id to fetch data
watch(() => stock.id, (newId) => {
  if (newId) {
    fetchStockData(newId);
  } else {
    resetForm();
  }
});

// Handle create stock
const handleCreate = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    const newStock = {...stock};
    delete newStock.id;

    const createdStock = await createStock(newStock);
    message.value = 'Stock created successfully!';
    messageType.value = 'success';

    // Set the ID of the newly created stock
    stock.id = createdStock.id;
  } catch (error) {
    message.value = `Error creating stock: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};

// Handle update stock
const handleUpdate = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    await updateStock(stock);
    message.value = 'Stock updated successfully!';
    messageType.value = 'success';
  } catch (error) {
    message.value = `Error updating stock: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};

// Handle delete stock
const handleDelete = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    await deleteStock(stock.id);
    message.value = 'Stock deleted successfully!';
    messageType.value = 'success';
    resetForm();
  } catch (error) {
    message.value = `Error deleting stock: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<style scoped>
.stock-management-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f9f9f9;
  padding: 120px;
  position: relative;
  border-radius: 20px;
}

.stock-management-form {
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

.stock-management-form h1 {
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

.form-group input {
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