package com.example.fintrail.controllers;

import com.example.fintrail.entities.Asset;
import com.example.fintrail.repositories.AssetRepository;
import com.example.fintrail.services.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Crucial for Vue.js connection!
public class TradeController {

    private final TradeService tradeService;
    private final AssetRepository assetRepository;

    // 1. READ: Get all assets for the dashboard
    @GetMapping("/assets")
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    // 2. CREATE: To execute a trade
    @PostMapping("/trade")
    public String executeTrade(
            @RequestParam Long userId,
            @RequestParam Long assetId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        return tradeService.purchaseAsset(userId, assetId, quantity);
    }

    // 3. FORECAST: Generate 7-day simulated price data
    @GetMapping("/assets/{id}/forecast")
    public List<BigDecimal> getAssetForecast(@PathVariable Long id) {
        List<BigDecimal> forecast = new ArrayList<>();

        // Find the asset to get its current price
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        BigDecimal currentPrice = asset.getPrice();
        Random random = new Random();

        // Generate 7 days of simulated movement
        for (int i = 0; i < 7; i++) {
            // Random change between -5% and +5%
            double changePercent = (random.nextDouble() * 0.1) - 0.05;
            currentPrice = currentPrice.multiply(BigDecimal.valueOf(1 + changePercent));
            forecast.add(currentPrice.setScale(2, RoundingMode.HALF_UP));
        }

        return forecast;
    }
}