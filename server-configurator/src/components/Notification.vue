<template>
  <div v-if="isVisible"
       :class="['alert', `alert-${type}`]">
    {{ message }}
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue';

export default {
  props: {
    message: {
      type: String,
      required: true
    },
    timeAlive: {
      type: Number,
      default: 3000
    },
    type: {
      type: String,
      default: 'success',
      validator: value => ['success', 'error'].includes(value)
    }
  },
  setup(props) {
    const isVisible = ref(false);

    let timeoutId = null;

    const showAlert = () => {
      isVisible.value = true;
      if (timeoutId) {
        clearTimeout(timeoutId);
      }
      timeoutId = setTimeout(() => {
        isVisible.value = false;
      }, props.timeAlive);
    };

    onMounted(() => {
      showAlert();
    });

    onUnmounted(() => {
      if (timeoutId) clearTimeout(timeoutId);
    });

    return {
      isVisible,
    }
  }
};
</script>

<style scoped>
:root {
  --duration: v-bind("duration");
}

.alert {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  padding: 10px 20px;
  color: white;
  border-radius: 5px;
  animation: fadeInOut var(--duration, 3s) ease-out;
  z-index: 1000;
}

.alert-success {
  background-color: green;
}

.alert-error {
  background-color: red;
}

@keyframes fadeInOut {
  0%, 100% { opacity: 0; }
  10%, 90% { opacity: 1; }
}
</style>