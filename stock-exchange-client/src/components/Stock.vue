<template>
  <div class="stock-card" @click="handleClick">
    <div class="stock-info">
      <div class="stock-name">{{ stock.name }}</div>
      <div class="stock-symbol">{{ stock.symbol }}</div>
    </div>
    <div class="stock-value">{{ stock.price }}</div>
  </div>
</template>

<script setup>
const props = defineProps({
  stock: Object,
});

const emit = defineEmits(['select']);

function handleClick() {
  emit('select', props.stock);
}
</script>

<style scoped>
body {
  font-family: Arial, sans-serif;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0;
  background: #f4f4f9;
}

.stock-card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  width: 100%;
  max-width: 500px;
  margin: 0;
  box-sizing: border-box;
}

.stock-card:hover {
  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
  cursor: pointer;
}

.stock-info {
  display: flex;
  flex-direction: column;
  margin-left: 20px;
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

.stock-value {
  font-size: 24px;
  color: #4CAF50;
  font-weight: bold;
}
</style>