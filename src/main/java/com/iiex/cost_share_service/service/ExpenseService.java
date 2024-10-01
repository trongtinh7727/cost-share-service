package com.iiex.cost_share_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiex.cost_share_service.entity.Expense;
import com.iiex.cost_share_service.repository.ExpenseRepository;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    public List<Expense> getExpensesByUserAndGroup(Long userId, Long groupId){
        return expenseRepository.findByUser_UserIdAndGroup_GroupId(userId, groupId);
    }
}
