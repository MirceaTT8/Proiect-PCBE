<template>
  <div class="stock-card" @click="handleClick">
    <div class="stock-info">
      <div class="stock-name">{{ stock.name }}</div>
      <div class="stock-symbol">{{ stock.symbol }}</div>
    </div>
    <div :class="priceClass" class="stock-value">
      {{ stock.price.toFixed(2) }}
      <span id="currency">EUR</span>
      <span v-if="priceChange !== null">({{ priceChange.toFixed(2) }}%)</span>
    </div>
  </div>
</template>
<script setup>
import {computed} from "vue";

const props = defineProps({
  stock: Object,
});

const emit = defineEmits(['select']);

function handleClick() {
  emit('select', props.stock);
}

const priceChange = computed(() => {
  if (
      props.stock.priceADayBefore === undefined ||
      props.stock.priceADayBefore === null ||
      props.stock.priceADayBefore === 0
  ) {
    return null;
  }
  return ((props.stock.price - props.stock.priceADayBefore) / props.stock.priceADayBefore) * 100;
});

const priceClass = computed(() => {
  if (priceChange.value === null) {
    return 'stock-value-neutral';
  }
  if (priceChange.value > 0.05) {
    return 'stock-value-up';
  } else if (priceChange.value < -0.05) {
    return 'stock-value-down';
  } else {
    return 'stock-value-neutral';
  }
});
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

.stock-value span {
  font-size: 18px;
  font-weight: lighter;
}

.stock-value-up {
  color: #4CAF50;
}

.stock-value-down {
  color: #F44336;
}

.stock-value-neutral {
  color: #000000;
}

#currency {
  font-size: 14px;
  margin-right: 5px;
}
</style>