package com.example.fintrail.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // e.g., "Blue Chip Mutual Fund"
    private String ticker;      // e.g., "BCMF"
    private BigDecimal price;   // Current dummy price
    private String riskLevel;   // Matches User's appetite
}