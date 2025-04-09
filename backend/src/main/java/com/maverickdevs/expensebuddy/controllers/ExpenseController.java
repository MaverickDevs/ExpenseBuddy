package com.maverickdevs.expensebuddy.controllers;

import com.maverickdevs.expensebuddy.dto.request.ExpenseRequestDTO;
import com.maverickdevs.expensebuddy.dto.response.ExpenseResponseDTO;
import com.maverickdevs.expensebuddy.dto.response.ExpenseWithSplitsResponseDTO;
import com.maverickdevs.expensebuddy.dto.response.ExpenseWithoutSplitsResponseDTO;
import com.maverickdevs.expensebuddy.services.impl.ExpenseServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseServiceImpl expenseService;

    public ExpenseController(ExpenseServiceImpl expenseService){
        this.expenseService = expenseService;
    }


    //Get all expenses related to a group
    @GetMapping("/{groupId}/expenses")
    public ResponseEntity<List<ExpenseWithoutSplitsResponseDTO>> getExpenses(@PathVariable("groupId") Integer groupId) {
        List<ExpenseWithoutSplitsResponseDTO> expenseResponseDTOS = expenseService.getExpensesByGroupId(groupId);
        if (expenseResponseDTOS == null || expenseResponseDTOS.isEmpty()) {
            throw new EntityNotFoundException("No expenses found for group ID: " + groupId);
        }
        return ResponseEntity.ok(expenseResponseDTOS);
    }

    @GetMapping("/{groupId}/expenses/{expenseId}/splits")
    public ResponseEntity<ExpenseWithSplitsResponseDTO> getExpenseWithSplits(@PathVariable("expenseId") UUID expenseId){
        ExpenseWithSplitsResponseDTO expenseWithSplitsResponseDTO = expenseService.getExpenseWithSplits(expenseId);
        return ResponseEntity.ok(expenseWithSplitsResponseDTO);
    }

    @PostMapping("/addexpense")
    public ResponseEntity<?> addExpense( @RequestBody ExpenseRequestDTO expenseRequestDTO){
        System.out.println("Incoming JSON: " + expenseRequestDTO);
        ExpenseResponseDTO expenseResponseDTO = expenseService.addExpense(expenseRequestDTO);
        if (expenseResponseDTO == null) {
            throw new EntityNotFoundException("Failed to add expense");
        }
        return ResponseEntity.ok(expenseResponseDTO);
    }

    @DeleteMapping("/deleteexpense/{expenseId}")
    public ResponseEntity<?> deleteExpense(@PathVariable("expenseId") UUID expenseId){
        return ResponseEntity.ok(expenseService.deleteExpense(expenseId));
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(expenseService.getAll());
    }
}
