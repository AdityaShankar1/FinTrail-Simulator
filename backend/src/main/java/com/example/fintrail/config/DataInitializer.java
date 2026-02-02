package com.example.fintrail.config;

import com.example.fintrail.entities.Asset;
import com.example.fintrail.repositories.AssetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(AssetRepository repository) {
        return args -> {
            // Only seed if the database is empty (Idempotency)
            if (repository.count() == 0) {
                Asset fund1 = new Asset(null, "Safe-T Index Fund", "STIF", BigDecimal.valueOf(150.00), "LOW");
                Asset fund2 = new Asset(null, "Growth Matrix Mutual", "GMM", BigDecimal.valueOf(320.50), "MEDIUM");
                Asset fund3 = new Asset(null, "Crypto Venture Fund", "CVF", BigDecimal.valueOf(10.25), "HIGH");

                repository.saveAll(List.of(fund1, fund2, fund3));
                System.out.println(">> Database Seeded with Dummy Assets!");
            }
        };
    }
}