<template>
  <h1>My Transactions</h1>
  <div class="container">
    <div class="transactions" v-if="transactions.length">
      <TransactionList v-if="loadedTransactions"
                 :transactions="transactions"
                 @order-selected="handleTransactionSelected"
      />
    </div>
    <h3 v-else>No transactions found</h3>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {getCurrentUser} from "@/services/userService.js";
import {fetchOrdersByUserId} from "@/services/orderService.js";
import TransactionList from "@/components/TransactionList.vue";
import {fetchStocks} from "@/services/stockService.js";
import {fetchTransactions} from "@/services/transactionService.js";

const transactions = ref([]);
const selectedTransaction = ref();

const loadedTransactions = ref(false);

const attachStockData = async (transactions) => {
  const stocks = await fetchStocks();
  transactions.forEach((transaction) => {
    transaction.stock = stocks.find(stock => stock.id === transaction.stockId);
  })
  return transactions;
};

onMounted(async () => {
  const fetchedTransactions = await fetchTransactions(getCurrentUser().id);

  transactions.value = await attachStockData(fetchedTransactions);

  if (transactions.value.length > 0) {
    selectedTransaction.value = transactions.value[0];
  } else {
    selectedTransaction.value = null;
  }

  loadedTransactions.value = true;
});

const handleTransactionSelected = (order) => {
  selectedTransaction.value = order;
}

</script>

<style scoped>
.container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-around;
}
</style>