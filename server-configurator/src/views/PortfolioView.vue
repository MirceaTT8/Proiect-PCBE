<template>
  <div class="portfolio-management-container">
    <div class="portfolio-management-form">
      <h1>Portfolio Management</h1>
      <form @submit.prevent="">
        <div class="form-group">
          <label for="id">Portfolio ID</label>
          <input
              id="id"
              v-model="portfolio.id"
              type="text"
              placeholder="Enter portfolio ID to load data or leave empty to create new portfolio"
          />
        </div>
        <div class="form-group">
          <label for="stockId">Stock ID</label>
          <input id="stockId" v-model="portfolio.stockId" type="text" required />
        </div>
        <div class="form-group">
          <label for="quantity">Quantity</label>
          <input id="quantity" v-model.number="portfolio.quantity" type="number" min="1" required />
        </div>
        <div class="form-group">
          <label for="userId">User ID</label>
          <input id="userId" v-model="portfolio.userId" type="text" required />
        </div>
        <div class="button-group">
          <button
              v-if="!portfolio.id"
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
import { fetchPortfolioById, createPortfolio, updatePortfolio, deletePortfolio } from '@/services/portfolioService.js';
import Notification from '@/components/Notification.vue';

// Reactive portfolio object including the id field
const portfolio = reactive({
  id: '',
  stockId: '',
  quantity: 0,
  userId: '',
});

// State variables
const isSubmitting = ref(false);
const message = ref('');
const messageType = ref('');

// Reset form fields
const resetForm = () => {
  portfolio.stockId = '';
  portfolio.quantity = 0;
  portfolio.userId = '';
  portfolio.id = '';
};

// Fetch portfolio data when id changes
const fetchPortfolioData = async (id) => {
  try {
    if (id) {
      const portfolioData = await fetchPortfolioById(id);
      if (portfolioData) {
        Object.assign(portfolio, portfolioData);
        message.value = '';
      } else {
        resetForm();
        message.value = 'Portfolio not found.';
        messageType.value = 'error';
      }
    } else {
      resetForm();
      message.value = '';
    }
  } catch (error) {
    resetForm();
    message.value = `Error fetching portfolio: ${error.message}`;
    messageType.value = 'error';
  }
};

// Watch for changes in portfolio.id to fetch data
watch(() => portfolio.id, (newId) => {
  if (newId) {
    fetchPortfolioData(newId);
  } else {
    resetForm();
  }
});

// Handle create portfolio
const handleCreate = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    const newPortfolio = { ...portfolio };
    delete newPortfolio.id;

    const createdPortfolio = await createPortfolio(newPortfolio);
    message.value = 'Portfolio created successfully!';
    messageType.value = 'success';

    // Set the ID of the newly created portfolio
    portfolio.id = createdPortfolio.id;
  } catch (error) {
    message.value = `Error creating portfolio: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};

// Handle update portfolio
const handleUpdate = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    await updatePortfolio(portfolio);
    message.value = 'Portfolio updated successfully!';
    messageType.value = 'success';
  } catch (error) {
    message.value = `Error updating portfolio: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};

// Handle delete portfolio
const handleDelete = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    await deletePortfolio(portfolio.id);
    message.value = 'Portfolio deleted successfully!';
    messageType.value = 'success';
    resetForm();
  } catch (error) {
    message.value = `Error deleting portfolio: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<style scoped>
.portfolio-management-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f9f9f9;
  padding: 120px;
  position: relative;
  border-radius: 20px;
}

.portfolio-management-form {
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

.portfolio-management-form h1 {
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