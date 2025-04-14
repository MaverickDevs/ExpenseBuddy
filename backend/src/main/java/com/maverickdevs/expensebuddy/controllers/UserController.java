package com.maverickdevs.expensebuddy.controllers;

import com.maverickdevs.expensebuddy.entities.CategoryType;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.services.impl.ExpenseServiceImpl;
import com.maverickdevs.expensebuddy.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userService;
    private final ExpenseServiceImpl expenseService;

    public UserController(UserServiceImpl userService, ExpenseServiceImpl expenseService) {
        this.userService = userService;
        this.expenseService = expenseService;
    }


    @GetMapping("/getall")
    public List<User> getallusers(){
        return userService.getallusers();
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query){
        List<User> users =  userService.searchUsers(query);
        if(users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}/splits")
    public ResponseEntity<Map<CategoryType, BigDecimal>> getUserExpenses(@PathVariable Integer userId, @RequestParam(defaultValue = "false") boolean includeNotSettled){
        Map<CategoryType,BigDecimal> res = expenseService.getUserSplits(userId, includeNotSettled);
        return ResponseEntity.ok(res);
    }
}
