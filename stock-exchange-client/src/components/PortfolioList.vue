<template>
  <div class="portfolio-list">
    <div
        class="portfolio-item"
        v-for="portfolioItem in portfolios"
        :key="portfolioItem.id"
    >
      <!-- Portfolio Display -->
      <Portfolio
          :portfolio="portfolioItem"
          @select="handleSelect"
      />

      <!-- Action Buttons -->
      <div class="actions">
        <button @click="handleDelete(portfolioItem.id)">Delete</button>
<!--        <button @click="handleUpdate(portfolioItem)">Update</button>-->
      </div>
    </div>
  </div>
</template>

<script setup>
import Portfolio from "@/components/Portfolio.vue";

// Props: List of portfolios to display
const props = defineProps({
  portfolios: {
    type: Array,
    required: true,
    default: () => [],
  },
});

// Emits: Events for portfolio selection, deletion, and updating
const emit = defineEmits(['portfolioSelected', 'deletePortfolio', 'updatePortfolio']);

// Handle selecting a portfolio
const handleSelect = (portfolio) => {
  emit('portfolioSelected', portfolio);
};

// Handle deleting a portfolio
const handleDelete = (portfolioId) => {
  emit('deletePortfolio', portfolioId);
};

// Handle updating a portfolio
const handleUpdate = (portfolio) => {
  emit('updatePortfolio', portfolio);
};
</script>

<style scoped>
.portfolio-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 15px;
  padding: 20px;
  width: 100%;
  max-width: 600px;
  box-sizing: border-box;
}

.portfolio-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 15px;
  width: 100%;
  box-sizing: border-box;
}

.actions {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

button {
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  background-color: #007bff;
  color: white;
}

button:hover {
  background-color: #0056b3;
}

button:active {
  background-color: #003f7f;
}
</style>
