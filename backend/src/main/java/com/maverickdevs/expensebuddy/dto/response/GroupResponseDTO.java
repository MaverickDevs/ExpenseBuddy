package com.maverickdevs.expensebuddy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDTO {

    private Integer groupId;

    private String name;

    private BigDecimal owedAmount;

    private Number numberOfPeople;

    private LocalDateTime lastModifiedAt;
}
