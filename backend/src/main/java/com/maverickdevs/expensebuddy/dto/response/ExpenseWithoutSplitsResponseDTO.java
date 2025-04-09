package com.maverickdevs.expensebuddy.dto.response;

import com.maverickdevs.expensebuddy.entities.CategoryType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ExpenseWithoutSplitsResponseDTO {
    private UUID expenseId;
    private Integer paidById;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private CategoryType category;
}
