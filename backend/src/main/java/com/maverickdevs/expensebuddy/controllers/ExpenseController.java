package com.maverickdevs.expensebuddy.controllers;

import com.maverickdevs.expensebuddy.dto.request.ExpenseRequestDTO;
import com.maverickdevs.expensebuddy.entities.Expense;
import com.maverickdevs.expensebuddy.services.impl.ExpenseServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseServiceImpl expenseService;

    public ExpenseController(ExpenseServiceImpl expenseService){
        this.expenseService = expenseService;
    }


    //Get all expenses related to a group
    @GetMapping("/{groupId}/expenses")
    public ResponseEntity<Page<?>> getExpenses(@PathVariable("groupId") Integer groupId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Page<Expense> expenses = expenseService.getExpensesByGroupId(groupId, page, size);
        if (expenses == null || expenses.isEmpty()) {
            throw new EntityNotFoundException("No expenses found for group ID: " + groupId);
        }
        return ResponseEntity.ok(expenses);
    }

    @PostMapping("/addexpense")
    public ResponseEntity<?> addExpense( ExpenseRequestDTO expenseRequestDTO){
        Expense expense = expenseService.addExpense(expenseRequestDTO);
        if (expense == null) {
            throw new EntityNotFoundException("Failed to add expense");
        }
        return ResponseEntity.ok(expense);
    }
}
