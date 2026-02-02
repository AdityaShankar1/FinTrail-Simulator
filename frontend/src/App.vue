<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import RiskChart from './components/RiskChart.vue'
import ForecastChart from './components/ForecastChart.vue'

const assets = ref([])
const loading = ref(true)
const forecastData = ref(null)
const selectedTicker = ref('')
const userId = 1

const fetchAssets = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/assets')
    assets.value = response.data
    loading.value = false
  } catch (error) {
    console.error("Connection failed:", error)
  }
}

onMounted(fetchAssets)

const buyAsset = async (assetId) => {
  try {
    const response = await axios.post(`http://localhost:8080/api/trade`, null, {
      params: { userId: userId, assetId: assetId, quantity: 1 }
    });
    alert(response.data);
    await fetchAssets();
  } catch (error) {
    alert("Trade failed! Check if backend is running.");
  }
}

const showForecast = async (id, ticker) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/assets/${id}/forecast`)
    forecastData.value = response.data
    selectedTicker.value = ticker
  } catch (error) {
    alert("Could not fetch forecast. Make sure the backend endpoint is added!");
  }
}

const getProjectedValue = (price, risk) => {
  const factor = risk === 'HIGH' ? 1.4 : risk === 'MEDIUM' ? 1.15 : 1.05;
  return (price * factor).toFixed(2);
}
</script>

<template>
  <div class="dashboard">
    <header>
      <h1>FinTrail <span class="badge">Simulator</span></h1>
      <p>Your Virtual Investment Sandbox</p>
    </header>

    <main v-if="!loading">
      <section class="stats">
        <div class="card chart-card">
          <h3>Portfolio Risk Distribution</h3>
          <RiskChart :assetData="assets" />
        </div>

        <div class="card table-card">
          <h3>Market Terminal</h3>
          <table>
            <thead>
            <tr>
              <th>Ticker</th>
              <th>Price</th>
              <th>Risk</th>
              <th>1Y Projection</th>
              <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="asset in assets" :key="asset.id">
              <td><strong>{{ asset.ticker }}</strong></td>
              <td>${{ asset.price }}</td>
              <td :class="asset.riskLevel.toLowerCase()">{{ asset.riskLevel }}</td>
              <td class="projection">${{ getProjectedValue(asset.price, asset.riskLevel) }}*</td>
              <td>
                <button @click="buyAsset(asset.id)" class="buy-btn">Invest $1</button>
                <button @click="showForecast(asset.id, asset.ticker)" class="forecast-btn">Forecast</button>
              </td>
            </tr>
            </tbody>
          </table>
          <p class="disclaimer">*Simulated using Gaussian distribution logic.</p>
        </div>
      </section>

      <section v-if="forecastData" class="forecast-section">
        <div class="card forecast-card">
          <h3>10-Day Market Simulation: {{ selectedTicker }}</h3>
          <ForecastChart :forecastData="forecastData" :ticker="selectedTicker" />
          <p class="disclaimer">Note: This represents one possible stochastic path based on asset volatility.</p>
        </div>
      </section>
    </main>
  </div>
</template>

<style scoped>
.dashboard { font-family: 'Inter', sans-serif; padding: 40px; background: #f3f4f6; min-height: 100vh; color: #1f2937; }
header { margin-bottom: 30px; }
.badge { background: #10b981; color: white; padding: 4px 10px; border-radius: 6px; font-size: 0.4em; vertical-align: middle; text-transform: uppercase; }
.stats { display: flex; gap: 24px; align-items: flex-start; margin-bottom: 24px; }
.card { background: white; padding: 24px; border-radius: 16px; box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1); }
.chart-card { flex: 1; display: flex; flex-direction: column; align-items: center; }
.table-card { flex: 2; }

table { width: 100%; border-collapse: collapse; }
th { text-align: left; color: #6b7280; font-size: 0.875rem; text-transform: uppercase; padding-bottom: 12px; }
td { padding: 16px 0; border-bottom: 1px solid #f3f4f6; }

.high { color: #ef4444; font-weight: 600; }
.medium { color: #f59e0b; font-weight: 600; }
.low { color: #10b981; font-weight: 600; }
.projection { color: #6366f1; font-style: italic; }

.buy-btn {
  background: #2563eb; color: white; border: none; padding: 8px 16px;
  border-radius: 8px; cursor: pointer; font-weight: 500; transition: background 0.2s;
  margin-right: 8px;
}
.buy-btn:hover { background: #1d4ed8; }

.forecast-btn {
  background: white; color: #6366f1; border: 1px solid #6366f1; padding: 7px 15px;
  border-radius: 8px; cursor: pointer; font-weight: 500; transition: all 0.2s;
}
.forecast-btn:hover { background: #f5f3ff; border-color: #4f46e5; }

.forecast-section { animation: slideIn 0.4s ease-out; }
.disclaimer { font-size: 0.7rem; color: #9ca3af; margin-top: 15px; }

@keyframes slideIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>