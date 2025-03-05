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
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID expenseId;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "paid_by", nullable = false)
    private User paidBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseType expenseType;

    private BigDecimal amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    private LocalDateTime createdAt;

    @PrePersist
    @PreUpdate
    private void validateExpense() {
        if (expenseType == ExpenseType.GROUP && group == null) {
            throw new IllegalArgumentException("Group expenses must have a group.");
        }
        if (expenseType == ExpenseType.PERSONAL && group != null) {
            throw new IllegalArgumentException("Personal expenses cannot have a group.");
        }
    }

    // Getters and Setters
}
