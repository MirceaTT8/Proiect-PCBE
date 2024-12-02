<template>
  <div class="portfolio-view">
    <h1>User Portfolio</h1>
    <Portfolio
        :stockId="5"
        :stockSymbol="'AAPL'"
        :stockQuantity="10"
        :euroValue="50"
    />
  </div>
</template>

<script>
import Portfolio from '@/components/Portfolio.vue';

export default {
  name: 'PortfolioView',
  components: {
    Portfolio,
  },
};
</script>

<style scoped>
.portfolio-view {
  text-align: center;
  padding: 2rem;
}

h1 {
  margin-bottom: 1.5rem;
  color: #333;
}
</style>
