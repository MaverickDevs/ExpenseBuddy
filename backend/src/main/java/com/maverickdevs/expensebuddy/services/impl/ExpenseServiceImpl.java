package com.maverickdevs.expensebuddy.services.impl;

import com.maverickdevs.expensebuddy.entities.Expense;
import com.maverickdevs.expensebuddy.repositories.ExpenseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    public Page<Expense> getExpensesByGroupId(Integer groupId, int page, int size) {
        return expenseRepository.findByGroupId(groupId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }
}
