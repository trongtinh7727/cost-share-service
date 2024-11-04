package com.iiex.cost_share_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iiex.cost_share_service.dto.request.CreateExpenseRequest;
import com.iiex.cost_share_service.dto.response.ApiResponse;
import com.iiex.cost_share_service.entity.Expense;
import com.iiex.cost_share_service.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // Tạo chi tiêu
    @PostMapping
    public ResponseEntity<ApiResponse<Expense>> createExpense(
            @RequestBody CreateExpenseRequest request) {
        try {
            Expense data = expenseService.createExpense(request.getExpense(), request.getGroupId(), request.getUserId(),
                    request.getSplits());
            ApiResponse<Expense> response = new ApiResponse<>(200, "Expense created successfully", data);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<Expense> response = new ApiResponse<>(400, ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Lấy danh sách chi tiêu theo nhóm
    @GetMapping("/group/{groupId}")
    public ResponseEntity<ApiResponse<List<Expense>>> getExpensesByGroup(@PathVariable Long groupId) {
        try {
            List<Expense> data = expenseService.getExpensesByGroup(groupId);
            ApiResponse<List<Expense>> response = new ApiResponse<>(200, "Expenses retrieved successfully", data);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<List<Expense>> response = new ApiResponse<>(400, ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
