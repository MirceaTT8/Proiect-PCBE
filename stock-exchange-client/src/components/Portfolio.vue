<template>
  <div class="portfolio-card" :id="isCurrency ? 'currency' : null" @click="handleClick">
    <div class="portfolio-info">
      <div class="portfolio-name">{{ portfolio.stock.name }}</div>
      <div v-if="!isCurrency" class="portfolio-symbol">{{ portfolio.stock.symbol }}</div>
    </div>
    <div class="portfolio-quantity">{{ portfolio.quantity }}</div>
    <div class="evaluation">EUR {{ evaluatedPrice }}</div>
  </div>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  portfolio: Object,
  required: true,
});

const emit = defineEmits(['select']);

const isCurrency = computed(() => {
  return props.portfolio.stock.symbol.startsWith("$");
});

const evaluatedPrice = computed(() => {
  return props.portfolio.quantity * props.portfolio.stock.price;
});

function handleClick() {
  emit('select', props.portfolio);
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

.portfolio-card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  width: 500px;
  max-width: 700px;
  margin: 0;
  box-sizing: border-box;
}

#currency {
  background: #f1f1f1;
}

#currency:hover {
  background: #f1f1f1;
  cursor: default;
}

.portfolio-card:hover {
  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
  cursor: pointer;
}

.portfolio-info {
  display: flex;
  flex-direction: column;
  margin-left: 20px;
}

.portfolio-name {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.portfolio-symbol {
  font-size: 14px;
  color: #666;
}

.portfolio-quantity {
  font-size: 24px;
  color: #4CAF50;
  font-weight: bold;
}
</style>