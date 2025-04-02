package com.maverickdevs.expensebuddy.services.impl;

import com.maverickdevs.expensebuddy.dto.request.ExpenseRequestDTO;
import com.maverickdevs.expensebuddy.dto.request.UserShareDTO;
import com.maverickdevs.expensebuddy.dto.response.ExpenseResponseDTO;
import com.maverickdevs.expensebuddy.dto.response.UserDTO;
import com.maverickdevs.expensebuddy.entities.Expense;
import com.maverickdevs.expensebuddy.entities.ExpenseType;
import com.maverickdevs.expensebuddy.entities.Split;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.repositories.ExpenseRepository;
import com.maverickdevs.expensebuddy.repositories.GroupRepository;
import com.maverickdevs.expensebuddy.repositories.SplitRepository;
import com.maverickdevs.expensebuddy.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


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

//    public Page<Expense> getExpensesByGroupId(Integer groupId, int page, int size) {
//        return expenseRepository.findByGroupId(groupId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
//    }
public Page<ExpenseResponseDTO> getExpensesByGroupId(Integer groupId, int page, int size) {
    Page<Expense> expensesPage = expenseRepository.findByGroupId(groupId,
            PageRequest.of(page, size, Sort.by("createdAt").descending()));

    return expensesPage.map(expense -> ExpenseResponseDTO.builder()
            .expenseId(expense.getExpenseId())
            .groupId(expense.getGroup() != null ? expense.getGroup().getGroupId() : null)
            .paidBy(expense.getPaidBy())
            .expenseType(expense.getExpenseType())
            .amount(expense.getAmount())
            .description(expense.getDescription())
            .categoryType(expense.getCategory())
            .build());
}

    @Transactional
    public ExpenseResponseDTO addExpense(ExpenseRequestDTO expenseRequestDTO) {
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

            ExpenseResponseDTO expenseResponseDTO  = ExpenseResponseDTO.builder()
                    .categoryType(createdexpense.getCategory())
                    .expenseType(createdexpense.getExpenseType())
                    .paidBy(paid_by)
                    .amount(createdexpense.getAmount())
                    .description(createdexpense.getDescription())
                    .groupId(expenseRequestDTO.getGroupId())
                    .expenseId(createdexpense.getExpenseId())
                    .build();

            return expenseResponseDTO;

        } catch (Exception e) {
            log.error("Error occurred while adding expense: {}", e.getMessage(), e); // Log full exception
            throw new RuntimeException("Error occurred while adding expense", e);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteExpense(UUID expenseId) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new EntityNotFoundException("No expense found with this id"));

        List<Split> splits = splitRepository.getSplitsByExpense(expense);
        User creator = expense.getPaidBy();
        boolean flag = true;
        for(Split split : splits){
            if(!split.getDebtor().equals(creator) && split.getIsSettled()){
                flag = false;
                break;
            }

        }



        if(!flag){
            return createResponse("error","Cannot delete this expense as someone has already paid ");
        }
        for(Split split : splits){
            splitRepository.delete(split);
        }
        expenseRepository.delete(expense);
        return createResponse("success","Expense successfully deleted");
    }

    private ResponseEntity<Map<String, String>> createResponse(String key, String value) {
        Map<String, String> response = new HashMap<>();
        response.put(key, value);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getAll() {
        List<Expense> expenses =  expenseRepository.findAll();
        return ResponseEntity.ok(expenses);
    }
}
