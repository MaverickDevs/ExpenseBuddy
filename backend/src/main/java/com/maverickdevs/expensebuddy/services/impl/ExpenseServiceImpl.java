package com.maverickdevs.expensebuddy.services.impl;

import com.maverickdevs.expensebuddy.dto.request.ExpenseRequestDTO;
import com.maverickdevs.expensebuddy.dto.request.UserShareDTO;
import com.maverickdevs.expensebuddy.dto.response.*;
import com.maverickdevs.expensebuddy.entities.*;
import com.maverickdevs.expensebuddy.repositories.ExpenseRepository;
import com.maverickdevs.expensebuddy.repositories.GroupRepository;
import com.maverickdevs.expensebuddy.repositories.SplitRepository;
import com.maverickdevs.expensebuddy.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


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
public List<ExpenseWithoutSplitsResponseDTO> getExpensesByGroupId(Integer groupId) {

    Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
    return expenseRepository.findByGroupOrderByCreatedAtDesc(group).stream().map(expense -> {
        ExpenseWithoutSplitsResponseDTO dto = new ExpenseWithoutSplitsResponseDTO();
        dto.setExpenseId(expense.getExpenseId());
        dto.setPaidById(expense.getPaidBy().getId());
        dto.setAmount(expense.getAmount());
        dto.setDescription(expense.getDescription());
        dto.setCategory(expense.getCategory());
        dto.setCreatedAt(expense.getCreatedAt());
        return dto;
    }).collect(Collectors.toList());
}

public ExpenseWithSplitsResponseDTO getExpenseWithSplits(UUID expenseId){
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(()-> new ResourceAccessException("Expense Not Found"));

        ExpenseWithSplitsResponseDTO expenseDetails = new ExpenseWithSplitsResponseDTO();
        expenseDetails.setExpenseId(expense.getExpenseId());
        expenseDetails.setPaidBy(expense.getPaidBy().getId());
        expenseDetails.setAmount(expense.getAmount());
        expenseDetails.setDescription(expense.getDescription());

        List<Split> splits = splitRepository.findByExpense(expense);

        expenseDetails.setSplits(splits.stream().map(split ->{
            SplitResponseDTO dto = new SplitResponseDTO();
            dto.setSplitId(split.getSplitId());
            dto.setIsSettled(split.getIsSettled());
            dto.setSplitAmount(split.getSplitAmount());
            dto.setCreditorId(split.getCreditor().getId());
            dto.setDebtorId(split.getDebtor().getId());
            dto.setSettledAt(split.getSettledAt());

            return dto;
        }).collect(Collectors.toList()));

        return expenseDetails;
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
                    .group(expenseType.equals(ExpenseType.GROUP)?groupRepository.findById(expenseRequestDTO.getGroupId())
                            .orElseThrow(() -> new RuntimeException("Group not found")):(null))
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
                        .debtor(userRepository.findById(userShareDTO.getUserId())
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

        List<Split> splits = splitRepository.findByExpense(expense);
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

    public Map<CategoryType, BigDecimal> getUserSplits(Integer userId, boolean includeNotSettled) {
        List<Object[]> results;
        if (includeNotSettled) {
            results = splitRepository.findUserSpendingAllByCategory(userId);
        } else {
            results = splitRepository.findUserSpendingSettledByCategory(userId);
        }

        return results.stream().collect(Collectors.toMap(
                result -> (CategoryType) result[0],
                result -> (BigDecimal) result[1],
                BigDecimal::add
        ));
    }
}
