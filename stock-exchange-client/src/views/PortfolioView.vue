<template>
  <h1>This is PortfolioView</h1>
  <TextField labelTitle="custom label"/>
  <Portfolio stockId=5 stockSymbol="AAPL" stockQuantity=10 euroValue=50 />
</template>

<script setup>
import TextField from '@/components/TextField.vue';
import Portfolio from '@/components/Portfolio.vue';
</script>

<style>

</style>