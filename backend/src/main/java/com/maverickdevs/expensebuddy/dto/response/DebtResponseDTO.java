package com.maverickdevs.expensebuddy.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DebtResponseDTO {
    private Integer debtorId;
    private String debtorName;
    private BigDecimal debt;
}
