<template>
  <div id="app">
    <Navbar />
    <router-view/>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import Navbar from "@/components/Navbar.vue";

const router = useRouter()

const handleSelect = (index) => {
  router.push(index)
}
</script>

<style>
#app {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
}
</style>