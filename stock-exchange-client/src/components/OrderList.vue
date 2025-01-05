<template>
  <div class="order-list">
    <Order
        v-for="order in orders"
        :key="order.id"
        :order="order"
        @select="handleOrderSelected"
    />
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import Order from "@/components/Order.vue";

const props = defineProps({
  orders: {
    type: Array,
    required: true,
    default: () => []
  },
});

const emit = defineEmits(['orderSelected']);

function handleOrderSelected(order) {
  emit('orderSelected', order);
}
</script>

<style scoped>
.order-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
  max-height: 600px;
  overflow-y: auto;
  padding: 20px;
  max-width: fit-content;
}
</style>