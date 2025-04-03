package com.maverickdevs.expensebuddy.dto.response;

import com.maverickdevs.expensebuddy.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponseDTO {

    private Integer groupId;

    private String name;

    private BigDecimal owedAmount;

    private List<User> users;

    private LocalDateTime lastModifiedAt;
}
