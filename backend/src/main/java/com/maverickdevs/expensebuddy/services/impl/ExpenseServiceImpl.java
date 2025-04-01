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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseServiceImpl {

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
        User paid_by = userRepository.getById(expenseRequestDTO.getPaid_by());
        ExpenseType expenseType = expenseRequestDTO.getGroup_id() == 0 ? ExpenseType.PERSONAL : ExpenseType.GROUP;
        Expense expense = Expense.builder()
                .category(expenseRequestDTO.getCategory())
                .description(expenseRequestDTO.getDescription())
                .amount(expenseRequestDTO.getTotal_amount())
                .paidBy(paid_by)
                .group(groupRepository.getById((expenseRequestDTO.getGroup_id())))
                .createdAt(LocalDateTime.now())
                .expenseType(expenseType)
                .build();
        expense = expenseRepository.save(expense);
        List<UserShareDTO> userShareDTOList = expenseRequestDTO.getUserShareDTOList();
        for(UserShareDTO userShareDTO: userShareDTOList){
            //expenseRepository.save()
            boolean isSettledForCreator = userShareDTO.getUserId().equals(paid_by.getId());

            Split split = Split.builder()
                    .expense(expense)
                    .createdAt(LocalDateTime.now())
                    .creditor(paid_by)
                    .isSettled(isSettledForCreator)
                    .settledAt(isSettledForCreator ? LocalDateTime.now() : null)
                    .debtor(userRepository.getById(userShareDTO.getUserId()))
                    .splitAmount(userShareDTO.getShareAmount())
                    .build();
            splitRepository.save(split);
        }



        return expense;
    }
}
