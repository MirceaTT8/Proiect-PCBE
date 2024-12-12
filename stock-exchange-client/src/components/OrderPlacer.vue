<template>
  <div class="stock-order-card">
    <h2>Place Stock Order</h2>
    <div class="header">
      <div class="toggle-group">
        <label class="switch">
          <input type="checkbox" v-model="isSellOrder"/>
          <span class="slider"></span>
        </label>
        <span class="order-type-label">{{ orderTypeLabel }}</span>
      </div>
      <h3>{{ stock.name }}</h3>
      <p>{{ stock.symbol }}</p>
      <h3>€ {{ stock.price }}</h3>
    </div>
    <form @submit.prevent="submitOrder">
      <div class="form-row">
        <div class="form-group">
          <label for="quantity">Quantity:</label>
          <input
              type="number"
              id="quantity"
              v-model.number="quantity"
              min="1"
              required
          />
        </div>
        <div class="form-group">
          <label for="price">Price:</label>
          <input
              type="number"
              id="price"
              v-model.number="price"
              min="0"
              step="0.01"
              required
          />
        </div>
      </div>
      <div class="total-amount">
        Total Order Amount: € {{ totalAmount.toFixed(2) }}
      </div>
      <button type="submit" :disabled="isSubmitting">
        {{ isSubmitting ? 'Submitting...' : 'Place Order' }}
      </button>
    </form>
  </div>
  <Notification v-if="notificationMessage"
                :message="notificationMessage"
                :type="notificationType"
  />


</template>

<script setup>
import {ref, computed} from 'vue';
import Notification from "@/components/Notification.vue";
import {placeOrder} from "@/services/orderService.js";

const props = defineProps({
  stock: {
    type: Object,
    required: true,
    default: () => ({})
  },
});

const isSellOrder = ref(false);
const orderTypeLabel = computed(() => (isSellOrder.value ? 'Sell' : 'Buy'));
const quantity = ref(null);
const price = ref(null);
const isSubmitting = ref(false);

const notificationMessage = ref('');
const notificationType = ref('');

const totalAmount = computed(() => {
  return quantity.value * price.value || 0;
});

const submitOrder = async () => {
  if (isSubmitting.value) return;

  isSubmitting.value = true;

  const orderData = {
    orderType: orderTypeLabel.value,
    stockId: props.stock.id,
    quantity: quantity.value,
    price: price.value,
  };

  try {
    await placeOrder(orderData);

    notificationMessage.value = 'Order placed successfully.';
    notificationType.value = 'success';
  } catch (error) {
    console.error('Error submitting order:', error);
    notificationMessage.value = 'Failed to place order.';
    notificationType.value = 'error';
  } finally {
    isSubmitting.value = false;
    quantity.value = null;
    price.value = null;
  }
};
</script>

<style scoped>
.stock-order-card {
  max-width: 600px;
  margin: 40px auto;
  padding: 30px 25px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.stock-order-card h2 {
  text-align: center;
  margin-bottom: 25px;
  color: #333;
}

form {
  display: flex;
  flex-direction: column;
}

.form-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  flex-wrap: wrap;
}

.form-group {
  display: flex;
  flex-direction: column;
  flex: 1;
  margin-right: 20px;
}

.form-group:last-child {
  margin-right: 0;
}

.form-group label {
  margin-bottom: 8px;
  color: #555;
}

input[type='text'],
input[type='number'] {
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 16px;
}

input[type='number']::-webkit-inner-spin-button {
  margin: 0;
}

input[type='number']::-webkit-outer-spin-button {
  margin: 0;
}

input[type='number'] {
  -moz-appearance: textfield;
}

.header {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  gap: 40px;
}

.toggle-group {
  display: flex;
  align-items: center;
  flex: 1;
}

.switch {
  position: relative;
  display: inline-block;
  width: 50px;
  height: 28px;
  margin-right: 12px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  background-color: #28a745;
  border-radius: 28px;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  transition: background-color 0.3s;
}

.slider:before {
  position: absolute;
  content: '';
  height: 22px;
  width: 22px;
  left: 3px;
  bottom: 3px;
  background-color: white;
  border-radius: 50%;
  transition: transform 0.3s;
}

input:checked + .slider {
  background-color: #a10707;
}

input:checked + .slider:before {
  transform: translateX(22px);
}

.order-type-label {
  font-size: 1rem;
  color: #333;
  font-weight: 600;
}

button {
  padding: 14px 20px;
  background-color: #006eff;
  color: #fff;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 17px;
  align-self: center;
  margin-top: 10px;
  width: 200px;
}

button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

button:hover:not(:disabled) {
  background-color: #0056d2;
}

.total-amount {
  margin-top: 20px;
  font-size: 1.2em;
  text-align: center;
  color: #333;
}
</style>