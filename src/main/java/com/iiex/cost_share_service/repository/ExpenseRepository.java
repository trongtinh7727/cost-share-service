package com.iiex.cost_share_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iiex.cost_share_service.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e WHERE e.createdBy.userId = :userId AND e.group.groupId = :groupId")
    List<Expense> findByCreatedByAndGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);

    @Query("SELECT e FROM Expense e WHERE e.group.groupId = :groupId")
    List<Expense> findByGroup(Long groupId);
}
