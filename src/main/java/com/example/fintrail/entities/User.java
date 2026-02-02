package com.example.fintrail.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // 'user' is a reserved keyword in Postgres, so we use 'users'
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String riskAppetite; // "LOW", "MEDIUM", "HIGH"

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private VirtualAccount account;
}