package com.maverickdevs.expensebuddy.dto.request;

import com.maverickdevs.expensebuddy.entities.User;
import lombok.Getter;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class PaymentRequestDTO {

    private Integer debtorId;

    private Integer creditorId;

    private BigDecimal amount;

    private List<UUID> splitIdList;
}
