package com.maverickdevs.expensebuddy.dto.response;

import com.maverickdevs.expensebuddy.entities.CategoryType;
import com.maverickdevs.expensebuddy.entities.ExpenseType;
import com.maverickdevs.expensebuddy.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseResponseDTO {
    private UUID expenseId;

    private Integer groupId;

    private User paidBy;

    private ExpenseType expenseType;

    private BigDecimal amount;

    private String description;

    private CategoryType categoryType;

}
