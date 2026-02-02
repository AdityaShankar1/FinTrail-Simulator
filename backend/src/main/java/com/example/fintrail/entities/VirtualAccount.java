package com.example.fintrail.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VirtualAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance = BigDecimal.valueOf(10000.00); // Default dummy money

    // GRASP Principle: The Account is the "Information Expert"
    // It decides if a transaction is valid.
    public boolean canAfford(BigDecimal amount) {
        return this.balance.compareTo(amount) >= 0;
    }

    public void deduct(BigDecimal amount) {
        if (!canAfford(amount)) {
            throw new RuntimeException("Insufficient dummy funds!");
        }
        this.balance = this.balance.subtract(amount);
    }
}