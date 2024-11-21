package com.iiex.cost_share_service.service;

import java.util.List;

import com.iiex.cost_share_service.entity.Expense;

public interface IExpenseRedisService {
    public List<Expense> getExpensesFromCache(Long userId, Long groupId);

    public void saveExpensesToCache(Long userId, Long groupId, List<Expense> expenses);

    public void evictCache(Long userId, Long groupId);
}
