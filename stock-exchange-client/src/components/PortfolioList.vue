<template>
    <div class="portfolio-list">
        <Portfolio
            v-for="portfolioItem in portfolios"
        :key="portfolioItem.id"
        :portfolio="portfolioItem"
        @select="handleSelect"
        />
    </div>
</template>

<script setup>
import Portfolio from "@/components/Portfolio.vue";

const props = defineProps({
portfolios: {
  type: Array,
  required: true,
  default: () => []
}
});

const emit = defineEmits(['portfolioSelected']);

const handleSelect = (portfolio) => {
emit('portfolioSelected', portfolio);
}

</script>

<style scoped>
.portfolio-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 20px;
  width: 100%;
  max-width: 600px;
  box-sizing: border-box;
}
</style>