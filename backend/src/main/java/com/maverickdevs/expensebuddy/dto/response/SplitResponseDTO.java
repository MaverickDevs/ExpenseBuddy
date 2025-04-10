package com.maverickdevs.expensebuddy.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SplitResponseDTO {
    private UUID splitId;
    private Integer debtorId;   // Who owes money
    private Integer creditorId; // Who gets paid
    private BigDecimal splitAmount;
    private Boolean isSettled;
    private LocalDateTime settledAt;
}
