package com.iiex.cost_share_service.dto.request;

import lombok.Data;
import java.util.Map;

import com.iiex.cost_share_service.entity.Expense;

@Data
public class CreateExpenseRequest {
    private Expense expense;
    private Long groupId;
    private Long userId;
    private Map<Long, Double> splits;
}
