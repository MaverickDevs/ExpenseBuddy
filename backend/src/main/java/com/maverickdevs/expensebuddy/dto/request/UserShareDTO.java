package com.maverickdevs.expensebuddy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserShareDTO {
    private Integer userId;  // ID of the user
    private BigDecimal shareAmount; // Amount the user owes or is owed
}