<template>
  <div class="stock-chart-container">
    <div class="time-range-buttons">
      <button
          v-for="(range, index) in timeRanges"
          :key="index"
          :class="{ active: selectedRange === range.value }"
          @click="selectTimeRange(range.value)"
      >
        {{ range.label }}
      </button>
    </div>
    <v-chart :option="chartOption" :autoresize="true" class="line-chart"></v-chart>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue';
import { use } from 'echarts/core';

import VChart from 'vue-echarts';
import { LineChart } from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  DataZoomComponent,
} from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';
import {fetchStockHistory} from "@/services/stockHistoryService.js";

use([
  LineChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  DataZoomComponent,
  CanvasRenderer,
]);

const components = {
  'v-chart': VChart,
};

const props = defineProps({
  stock: {
    type: Object,
    required: true,
  },
});

const timeRanges = [
  { label: 'Today', value: '1' },
  { label: '1 Week', value: '7' },
  { label: '1 Month', value: '30' },
  { label: '6 Months', value: '180' },
  { label: '1 Year', value: '365' },
  { label: 'All', value: 'all' },
];

watch(() => props.stock, () => {
  updateChartOption();
})

const selectedRange = ref('7');
const chartOption = ref({});

const updateChartOption = async () => {
  const data = await fetchStockHistory(props.stock.id, selectedRange.value);

  const dates = data.map((item) => {
    const date = new Date(Number(item.timestamp));
    return date.toLocaleDateString();
  });
  const prices = data.map((item) => item.price);

  const lineColor = prices[0] >= prices[prices.length - 1] ? '#00C853' : '#D50000';

  chartOption.value = {
    title: {
      text: `${props.stock.name}: ${props.stock.symbol}`,
      left: 'center',
    },
    tooltip: {
      trigger: 'axis',
    },
    grid: {
      left: '10%',
      right: '10%',
      bottom: '15%',
      containLabel: true,
    },
    xAxis: {
      type: 'category',
      data: dates,
      axisLabel: {
        rotate: 45,
        formatter: (value) => value,
      },
      inverse: true,
    },
    yAxis: {
      type: 'value',
      scale: true,
      legend: false,
      axisLabel: {
        formatter: (value) => `$${value.toFixed(2)}`,
      },
    },
    series: [
      {
        name: 'Price',
        type: 'line',
        data: prices,
        smooth: true,
        lineStyle: {
          width: 2,
          color: lineColor,
        },
        showSymbol: false,
      },
    ],
    dataZoom: [
      {
        type: 'inside',
        start: 0,
        end: 100,
      },
      {
        type: 'slider',
        start: 0,
        end: 100,
        bottom: '0%',
      },
    ],
  };
};

const selectTimeRange = (range) => {
  selectedRange.value = range;
};

watch(selectedRange, () => {
  updateChartOption();
});

onMounted(() => {
  updateChartOption();
});
</script>

<style scoped>


.stock-chart-container {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.time-range-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-bottom: 1rem;
  box-sizing: border-box;
}

.time-range-buttons button {
  padding: 0.5rem 1rem;
  border: 1px solid #007bff;
  background-color: #fff;
  color: #007bff;
  cursor: pointer;
  border-radius: 4px;
  font-weight: bold;
}

.time-range-buttons button.active {
  background-color: #007bff;
  color: #fff;
}

.time-range-buttons button:hover {
  background-color: #0056b3;
  color: #fff;
}

.line-chart {
  width: 100%;
  height: 400px;
}
</style>