<template>
  <div class="transaction-card">
    <div class="transaction-info">
      <div class="transaction-stock">
        <div class="stock-name">{{ transaction.stock.name }}</div>
        <div class="stock-symbol">{{ transaction.stock.symbol }}</div>
      </div>
      <div class="transaction-details">
        <div class="transaction-quantity">Quantity: {{ transaction.quantity }}</div>
        <div class="transaction-price">Price: {{ transaction.price.toFixed(2) }} EUR</div>
        <div class="transaction-date">Date: {{ formattedDate }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  transaction: Object,
});

const formattedDate = computed(() => {
  const date = new Date(props.transaction.timestamp);
  return date.toLocaleDateString();
});
</script>

<style scoped>
.transaction-card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  padding: 20px;
  margin: 10px 0;
}

.transaction-info {
  display: flex;
  flex-direction: column;
}

.transaction-stock {
  margin-bottom: 10px;
}

.stock-name {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.stock-symbol {
  font-size: 14px;
  color: #666;
}

.transaction-details {
  display: flex;
  flex-direction: column;
}

.transaction-quantity,
.transaction-price,
.transaction-date {
  font-size: 16px;
  color: #333;
  margin: 2px 0;
}
</style>