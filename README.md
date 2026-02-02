# FinTrail Simulator 📈

A Full-Stack Financial Sandbox designed to simulate market volatility and risk-gated trading logic. Built with a **Modular Monolith** architecture.

## 🚀 Features
* **Gaussian Market Engine:** Backend logic simulates market "noise" using Gaussian distributions for price forecasting.
* **Risk-Gated Trading:** Prevents users from purchasing high-volatility assets if their profile is "Low Risk."
* **Real-time Analytics:** Interactive Portfolio distribution (Pie Chart) and 10-day price forecasting (Line Chart).
* **Modular Architecture:** Clean separation of concerns between Domain, Service, and Web layers.

## 🛠️ Tech Stack
* **Backend:** Java 25, Spring Boot 4, Spring Data JPA, PostgreSQL.
* **Frontend:** Vue.js 3, Vite, Chart.js, Axios.
* **DevOps:** Docker (PostgreSQL).

## 📦 How to Run
1.  **Database:** Run `docker run --name fintrail-db -e POSTGRES_PASSWORD=password123 -p 5432:5432 -d postgres:alpine`.
2.  **Backend:** Navigate to `/backend` and run `./mvnw spring-boot:run`.
3.  **Frontend:** Navigate to `/frontend`, run `npm install` then `npm run dev`.
