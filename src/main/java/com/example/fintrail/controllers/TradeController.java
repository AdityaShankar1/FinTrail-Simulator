
package com.example.fintrail.controllers;

import com.example.fintrail.entities.Asset;
import com.example.fintrail.repositories.AssetRepository;
import com.example.fintrail.services.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.fintrail.services.TradeService;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Crucial for Vue.js connection!
public class TradeController {

    private final TradeService tradeService;
    private final AssetRepository assetRepository;

    // 1. READ: For the Vue dashboard to show available funds
    @GetMapping("/assets")
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    // 2. CREATE: To execute a trade
    @PostMapping("/trade")
    public String executeTrade(
            @RequestParam Long userId,
            @RequestParam Long assetId,
            @RequestParam Integer quantity) {
        return tradeService.purchaseAsset(userId, assetId, quantity);
    }
}