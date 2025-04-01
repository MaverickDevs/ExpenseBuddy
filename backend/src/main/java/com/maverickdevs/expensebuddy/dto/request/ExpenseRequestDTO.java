package com.maverickdevs.expensebuddy.dto.request;

import com.maverickdevs.expensebuddy.entities.CategoryType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExpenseRequestDTO {

    private String description;

    @Enumerated
    private CategoryType category;

    private Integer paid_by;

    private Integer group_id;

    private BigDecimal total_amount;

    private List<UserShareDTO> userShareDTOList;



}


