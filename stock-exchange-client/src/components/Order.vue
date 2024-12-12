<template>
  <div class="order-card" @click="handleClick">
    <div class="order-symbol">{{ order.stock.symbol }}</div>
    <div class="order-info">
      <div class="order-data">Quantity: {{ order.quantity }}</div>
      <div class="order-data">Unit price: {{ order.price }}</div>
    </div>
    <div class="total-price">
      {{ totalPrice.toFixed(2) }} <span id="currency">EUR</span>
    </div>
  </div>
</template>

<script setup>
import {defineProps, defineEmits, computed} from "vue";

const props = defineProps({
  order: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(['select']);

function handleClick() {
  emit('select', props.order);
}

const totalPrice = computed(() => {
  return props.order.quantity * props.order.price;
})

</script>

<style scoped>
.order-card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  width: 500px;
  max-width: 700px;
  margin: 0;
  box-sizing: border-box;
  transition: box-shadow 0.3s ease;
}

.order-card:hover {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
  cursor: pointer;
}

.order-info {
  display: flex;
  flex-direction: column;
}

.order-symbol {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.order-data {
  font-size: 14px;
  color: #666;
}

.total-price {
  font-size: 24px;
  color: #4CAF50;
  font-weight: bold;
  display: flex;
  align-items: center;
}

.total-price span {
  font-size: 18px;
  font-weight: lighter;
  margin-left: 5px;
}

#currency {
  font-size: 14px;
}
</style>