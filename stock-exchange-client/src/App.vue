<script setup>


import { RouterView } from 'vue-router';
import  NavBar from '@/components/NavBar.vue'

import {onMounted} from "vue";
import {setCurrentUser} from "@/services/userService.js";
import {fetchDefaultTradingStock, setDefaultTradingStock} from "@/services/stockService.js";

onMounted(async () => {

  // For testing purposes

  const defaultTradingStock = await fetchDefaultTradingStock();

  setCurrentUser({});
  setDefaultTradingStock(defaultTradingStock);
  
});

</script>

<template>

  <header>

  </header>

  <main>
    <div class="container">
      <NavBar />
      <RouterView class="router-view" />
    </div>
  </main>
</template>

<style scoped>
.container {
  height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
}
.router-view {
  flex-grow: 1;
  display: flex;
}
</style>
