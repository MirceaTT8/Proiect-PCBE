<!-- StockList.vue -->
<template>
  <div class="stock-list">
    <Stock
        v-for="stock in stocks"
        :key="stock.symbol"
        :stock="stock"
        @select="handleSelect"
    />
  </div>
</template>

<script setup>
import Stock from "@/components/Stock.vue";

const props = defineProps({
  stocks: Array
});

const emit = defineEmits(['stockSelected']);

const handleSelect = (stock) => {
  emit('stockSelected', stock);
}

</script>

<style scoped>
.stock-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 20px;
  width: 100%;
  max-width: 600px;
  box-sizing: border-box;
}
</style>