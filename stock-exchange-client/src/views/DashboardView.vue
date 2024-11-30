<template>
  <h1>This is DashboardView</h1>

  <h1>Stock Id received: {{ stockId }}</h1>
</template>

<script>
export default {
  computed: {
    stockId() {
      return this.$route.params.stockId; // Accessing the route parameter
    },
  },
}
</script>

<style>

</style>