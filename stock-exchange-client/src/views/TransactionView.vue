<template>
  <div class="transaction-view">
    <h1>Transaction History</h1>
    <div v-if="transactions.length">
      <Transaction
          v-for="transaction in transactions"
          :key="transaction.id"
          :id="transaction.id"
          :amount="transaction.amount"
          :date="transaction.date"
          :description="transaction.description"
      />
    </div>
    <p v-else>No transactions available.</p>
  </div>
</template>

<script>
import Transaction from "@/components/Transaction.vue"; // Adjust the path if necessary

export default {
  name: "TransactionView",
  components: {
    Transaction,
  },
  data() {
    return {
      transactions: [
        {
          id: "1",
          amount: 150.75,
          date: "2023-12-01",
          description: "Payment for groceries",
        },
        {
          id: "2",
          amount: 50.0,
          date: "2023-12-02",
          description: "Movie ticket",
        },
        {
          id: "3",
          amount: 200.99,
          date: "2023-12-03",
          description: "Online shopping",
        },
      ],
    };
  },
};
</script>

<style scoped>
.transaction-view {
  padding: 2rem;
  text-align: center;
}

.transaction-view h1 {
  margin-bottom: 1.5rem;
  color: #333;
}

.transaction-view p {
  color: #777;
}
</style>
