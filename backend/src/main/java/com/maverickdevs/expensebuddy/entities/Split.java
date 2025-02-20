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
@Table(name = "splits")
public class Split {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID splitId;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "debtor_id", nullable = false)
    private User debtor;

    @ManyToOne
    @JoinColumn(name = "creditor_id", nullable = false)
    private User creditor;

    private BigDecimal splitAmount;

    private Boolean isSettled = false;

    private LocalDateTime createdAt;

    private LocalDateTime settledAt;

    // Getters and Setters
}
