package com.maverickdevs.expensebuddy.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ExpenseWithSplitsResponseDTO {
    private UUID expenseId;
    private Integer paidBy;
    private BigDecimal amount;
    private String description;
    private List<SplitResponseDTO> splits;
}
