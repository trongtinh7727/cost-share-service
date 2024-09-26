package com.iiex.cost_share_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iiex.cost_share_service.entities.Expense;
import com.iiex.cost_share_service.service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/user/{userId}/group/{groupId}")
    public ResponseEntity<List<Expense>> getExpensesByUserAndGroup(
            @PathVariable Long userId, 
            @PathVariable Long groupId) {
        
        List<Expense> expenses = expenseService.getExpensesByUserAndGroup(userId, groupId);
        return ResponseEntity.ok(expenses);
    }    
}
