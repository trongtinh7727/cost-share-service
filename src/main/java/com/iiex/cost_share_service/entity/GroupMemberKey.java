package com.iiex.cost_share_service.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
public class GroupMemberKey {
    private Long groupId;
    private Long userId;
}
