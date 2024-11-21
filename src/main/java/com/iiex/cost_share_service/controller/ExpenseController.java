package com.iiex.cost_share_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iiex.cost_share_service.dto.request.CreateExpenseRequest;
import com.iiex.cost_share_service.dto.response.ResponseVO;
import com.iiex.cost_share_service.entity.Expense;
import com.iiex.cost_share_service.service.ExpenseRedisService;
import com.iiex.cost_share_service.service.IExpenseService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private IExpenseService expenseService;

    @Autowired
    private ExpenseRedisService redisService;

    // Create an expense
    @PostMapping("/user/{userId}/group/{groupId}")
    public ResponseEntity<ResponseVO<Expense>> createExpense(
            @PathVariable Long userId,
            @PathVariable Long groupId,
            @RequestBody CreateExpenseRequest request) {
        Expense data = expenseService.createExpense(request.getExpense(), groupId, userId, request.getSplits());
        ResponseVO<Expense> response = ResponseVO.success("Expense created successfully", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get a list of expenses by group
    @GetMapping("/user/{userId}/group/{groupId}")
    public List<Expense> getExpensesByUserAndGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        List<Expense> expenses = redisService.getExpensesFromCache(userId, groupId);
        if (expenses == null || expenses.isEmpty()) {
            expenses = expenseService.getExpensesByUserAndGroup(userId, groupId);
            redisService.saveExpensesToCache(userId, groupId, expenses);
        }
        return expenses;
    }
}
