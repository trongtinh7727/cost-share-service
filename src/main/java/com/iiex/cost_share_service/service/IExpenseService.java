package com.iiex.cost_share_service.service;

import java.util.List;
import java.util.Map;

import com.iiex.cost_share_service.entity.Expense;

public interface IExpenseService {
    public List<Expense> getExpensesByUserAndGroup(Long userId, Long groupId);

    public Expense createExpense(Expense expense, Long groupId, Long userId, Map<Long, Double> splits);

    public List<Expense> getExpensesByGroup(Long groupId);
}
