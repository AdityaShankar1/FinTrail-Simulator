package com.example.fintrail.services;
import java.util.Random;
import com.example.fintrail.entities.*;
import com.example.fintrail.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final UserRepository userRepository;
    private final AssetRepository assetRepository;

    @Transactional
    public String purchaseAsset(Long userId, Long assetId, Integer quantity) {
        // 1. Fetch data from DB
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        // 2. Risk Profile Validation (The "USP" of your app)
        // High risk assets are blocked for Low risk users
        if (asset.getRiskLevel().equalsIgnoreCase("HIGH") &&
                user.getRiskAppetite().equalsIgnoreCase("LOW")) {
            return "Trade Rejected: This asset is too volatile for your conservative risk profile!";
        }

        // 3. Financial Calculation
        BigDecimal totalCost = asset.getPrice().multiply(BigDecimal.valueOf(quantity));
        VirtualAccount account = user.getAccount();

        // 4. Integrity Check (GRASP: Account knows its own limits)
        if (!account.canAfford(totalCost)) {
            return "Trade Rejected: Insufficient dummy funds. Your balance is $" + account.getBalance();
        }

        // 5. Execution
        account.deduct(totalCost);

        // Save the user (it will save the account balance too due to CascadeType.ALL)
        userRepository.save(user);

        return "Success! You purchased " + quantity + " units of " + asset.getName() +
                ". Remaining balance: $" + account.getBalance();
    }
    public BigDecimal calculateProjectedReturn(Asset asset) {
        Random random = new Random();
        double baseReturn;
        double volatility;

        // Use a switch to define "Risk Personalities"
        switch (asset.getRiskLevel().toUpperCase()) {
            case "LOW" -> { baseReturn = 0.05; volatility = 0.02; }    // 5% avg, low swing
            case "MEDIUM" -> { baseReturn = 0.10; volatility = 0.10; } // 10% avg, medium swing
            case "HIGH" -> { baseReturn = 0.15; volatility = 0.40; }   // 15% avg, HUGE swing
            default -> { baseReturn = 0.0; volatility = 0.0; }
        }

        // Gaussian math: random.nextGaussian() gives a bell-curve number
        // This simulates "Market Noise"
        double noise = random.nextGaussian() * volatility;
        double finalRate = baseReturn + noise;

        return asset.getPrice().multiply(BigDecimal.valueOf(1 + finalRate));
    }
}