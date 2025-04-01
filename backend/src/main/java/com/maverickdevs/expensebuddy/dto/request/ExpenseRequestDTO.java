package com.maverickdevs.expensebuddy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverickdevs.expensebuddy.entities.CategoryType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter


public class ExpenseRequestDTO {

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private CategoryType category;

    @JsonProperty("paid_by")
    private Integer paidBy; // Changed to camelCase

    @JsonProperty("group_id")
    private Integer groupId; // Changed to camelCase

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("userShareDTOList")
    private List<UserShareDTO> userShareDTOList;

}



