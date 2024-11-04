package com.iiex.cost_share_service.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GroupMemberKey {
    private Long groupId;
    private Long userId;
}
