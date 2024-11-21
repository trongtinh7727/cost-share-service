package com.iiex.cost_share_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiex.cost_share_service.entity.Expense;
import com.iiex.cost_share_service.entity.ExpenseSplit;
import com.iiex.cost_share_service.entity.ExpenseSplitKey;
import com.iiex.cost_share_service.entity.Group;
import com.iiex.cost_share_service.entity.User;
import com.iiex.cost_share_service.repository.ExpenseRepository;
import com.iiex.cost_share_service.repository.GroupRepository;
import com.iiex.cost_share_service.repository.UserRepository;

@Service
public class ExpenseService implements IExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Expense> getExpensesByUserAndGroup(Long userId, Long groupId) {
        return expenseRepository.findByCreatedByAndGroup(userId, groupId);
    }

    @Override
    public Expense createExpense(Expense expense, Long groupId, Long userId, Map<Long, Double> splits) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        expense.setCreatedBy(user);
        expense.setGroup(group);
        expense.setCreatedAt(LocalDateTime.now());
        Expense savedExpense = expenseRepository.save(expense);

        splits.forEach((memberId, amount) -> {
            User member = userRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
            ExpenseSplit split = new ExpenseSplit();
            ExpenseSplitKey key = new ExpenseSplitKey(savedExpense.getExpenseId(), memberId);
            split.setId(key);
            split.setExpense(savedExpense);
            split.setUser(member);
            split.setAmount(amount);
            savedExpense.getSplits().add(split);
        });

        return expenseRepository.save(savedExpense);
    }

    @Override
    public List<Expense> getExpensesByGroup(Long groupId) {
        return expenseRepository.findByGroup(groupId);
    }
}
