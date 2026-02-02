<template>
  <div class="chart-container">
    <h3>7-Day Price Forecast: {{ ticker }}</h3>
    <div class="chart-box">
      <div v-for="(val, index) in (forecastData || [])" :key="index" class="bar-wrapper">
        <div class="bar" :style="{ height: (val / maxVal * 100) + 'px' }">
          <span class="price-label">${{ val.toFixed(2) }}</span>
        </div>
        <div class="day-label">Day {{ index + 1 }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
// Changed 'data' to 'forecastData' here
const props = defineProps(['forecastData', 'ticker']);

const maxVal = computed(() => {
  if (!props.forecastData || props.forecastData.length === 0) return 1;
  return Math.max(...props.forecastData, 1);
});
</script>

<style scoped>
.chart-container { margin-top: 20px; padding: 20px; background: #ffffff; border-radius: 12px; border: 1px solid #e5e7eb; color: #374151; }
.chart-box { display: flex; align-items: flex-end; justify-content: space-around; height: 180px; padding-top: 30px; border-bottom: 2px solid #374151; }
.bar-wrapper { display: flex; flex-direction: column; align-items: center; flex: 1; }
.bar { width: 35px; background: #10b981; position: relative; transition: height 0.5s ease-out; border-radius: 4px 4px 0 0; }
.price-label { position: absolute; top: -25px; font-size: 10px; font-weight: bold; width: 50px; text-align: center; color: #111827; }
.day-label { font-size: 12px; margin-top: 8px; color: #6b7280; }
</style>
