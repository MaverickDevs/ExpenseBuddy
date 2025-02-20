package com.maverickdevs.expensebuddy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "debtor_id", nullable = false)
    private User debtor;

    @ManyToOne
    @JoinColumn(name = "creditor_id", nullable = false)
    private User creditor;

    private BigDecimal amount;

    private LocalDateTime paidAt;

    // Getters and Setters
}
