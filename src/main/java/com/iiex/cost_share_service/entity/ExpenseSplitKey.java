package com.iiex.cost_share_service.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class ExpenseSplitKey implements Serializable {
    private Long expenseId;
    private Long userId;
}