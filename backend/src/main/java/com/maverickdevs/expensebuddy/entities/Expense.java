package com.maverickdevs.expensebuddy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Getter
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID expenseId;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true, insertable = true, updatable = true)
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

    @JsonIgnore
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
