package com.iiex.cost_share_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iiex.cost_share_service.entities.Expense;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    
}