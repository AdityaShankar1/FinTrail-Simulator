<script setup>
import { Pie } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement, CategoryScale } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale)

const props = defineProps(['assetData'])

// Logic to count how many assets are in each risk category
const lowRiskCount = props.assetData.filter(a => a.riskLevel === 'LOW').length
const medRiskCount = props.assetData.filter(a => a.riskLevel === 'MEDIUM').length
const highRiskCount = props.assetData.filter(a => a.riskLevel === 'HIGH').length

const chartData = {
  labels: ['Low Risk', 'Medium Risk', 'High Risk'],
  datasets: [
    {
      backgroundColor: ['#4ade80', '#fbbf24', '#f87171'], // Green, Amber, Red
      data: [lowRiskCount, medRiskCount, highRiskCount]
    }
  ]
}

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false
}
</script>

<template>
  <div style="height: 300px; width: 300px;">
    <Pie :data="chartData" :options="chartOptions" />
  </div>
</template>