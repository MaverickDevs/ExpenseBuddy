package com.maverickdevs.expensebuddy.services.impl;

import com.maverickdevs.expensebuddy.dto.request.ExpenseRequestDTO;
import com.maverickdevs.expensebuddy.dto.request.UserShareDTO;
import com.maverickdevs.expensebuddy.entities.Expense;
import com.maverickdevs.expensebuddy.entities.ExpenseType;
import com.maverickdevs.expensebuddy.entities.Split;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.repositories.ExpenseRepository;
import com.maverickdevs.expensebuddy.repositories.GroupRepository;
import com.maverickdevs.expensebuddy.repositories.SplitRepository;
import com.maverickdevs.expensebuddy.repositories.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ExpenseServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    private final ExpenseRepository expenseRepository;

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    private final SplitRepository splitRepository;

    public ExpenseServiceImpl(SplitRepository splitRepository, UserRepository userRepository, GroupRepository groupRepository, ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.splitRepository = splitRepository;
    }

    public Page<Expense> getExpensesByGroupId(Integer groupId, int page, int size) {
        return expenseRepository.findByGroupId(groupId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @Transactional
    public Expense addExpense(ExpenseRequestDTO expenseRequestDTO) {
        try {
            User paid_by = userRepository.findById(expenseRequestDTO.getPaidBy())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            ExpenseType expenseType = expenseRequestDTO.getGroupId() == 0 ? ExpenseType.PERSONAL : ExpenseType.GROUP;

            Expense expense = Expense.builder()
                    .category(expenseRequestDTO.getCategory())
                    .description(expenseRequestDTO.getDescription())
                    .amount(expenseRequestDTO.getTotalAmount())
                    .paidBy(paid_by)
                    .group(groupRepository.findById(expenseRequestDTO.getGroupId())
                            .orElseThrow(() -> new RuntimeException("Group not found")))
                    .createdAt(LocalDateTime.now())
                    .expenseType(expenseType)
                    .build();

            Expense createdexpense = expenseRepository.save(expense);

            List<UserShareDTO> userShareDTOList = expenseRequestDTO.getUserShareDTOList();
            for (UserShareDTO userShareDTO : userShareDTOList) {
                boolean isSettledForCreator = userShareDTO.getUserId().equals(paid_by.getId());

                Split split = Split.builder()
                        .expense(createdexpense)
                        .createdAt(LocalDateTime.now())
                        .creditor(paid_by)
                        .isSettled(isSettledForCreator)
                        .settledAt(isSettledForCreator ? LocalDateTime.now() : null)
                        .debtor(userRepository.findById(expenseRequestDTO.getPaidBy())
                            .orElseThrow(() -> new RuntimeException("User not found")))
                        .splitAmount(userShareDTO.getShareAmount())
                        .build();

                splitRepository.save(split);
            }

            return createdexpense;

        } catch (Exception e) {
            log.error("Error occurred while adding expense: {}", e.getMessage(), e); // Log full exception
            throw new RuntimeException("Error occurred while adding expense", e);
        }
    }

}
