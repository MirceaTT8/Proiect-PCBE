<template>
  <div class="transaction">
    <h3>Transaction ID: {{ id }}</h3>
    <p><strong>Amount:</strong> ${{ amount }}</p>
    <p><strong>Date:</strong> {{ date }}</p>
    <p><strong>Description:</strong> {{ description }}</p>
  </div>
</template>

<script>
export default {
  name: "Transaction",
  props: {
    id: {
      type: String,
      required: true,
    },
    amount: {
      type: Number,
      required: true,
    },
    date: {
      type: String,
      required: true,
    },
    description: {
      type: String,
      default: "No description provided",
    },
  },
};
</script>

<style scoped>
.transaction {
  border: 1px solid #ccc;
  padding: 1rem;
  margin: 0.5rem 0;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.transaction h3 {
  margin: 0;
  color: #333;
}

.transaction p {
  margin: 0.5rem 0;
  color: #555;
}
</style>
