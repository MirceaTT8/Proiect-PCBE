<template>
  <div class="transaction-list">
    <Transaction
        v-for="transaction in transactions"
        :key="transaction.timestamp + transaction.stock.symbol"
        :transaction="transaction"
    />
  </div>
</template>

<script setup>
import Transaction from './Transaction.vue';

const props = defineProps({
  transactions: Array,
});
</script>

<style scoped>
.transaction-list {
  display: flex;
  flex-direction: column;
  width: 100%;
}
</style>