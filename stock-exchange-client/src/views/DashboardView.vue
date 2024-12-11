<template>
  <h1>This is DashboardView</h1>

  <h1>Stock Id received: {{ stockId }}</h1>

  <OrderPlacer stock-id="0"/>

</template>

<script>
import OrderPlacer from "@/components/OrderPlacer.vue";

export default {
  components: {
    OrderPlacer,
  },
  setup() {
  }
}
</script>

<style>

</style>