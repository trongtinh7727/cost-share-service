package com.iiex.cost_share_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iiex.cost_share_service.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByUser_UserIdAndGroup_GroupId(Long userId, Long groupId);
}
