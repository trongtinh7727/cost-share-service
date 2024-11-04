package com.iiex.cost_share_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iiex.cost_share_service.entity.Group;
import com.iiex.cost_share_service.entity.GroupMember;
import com.iiex.cost_share_service.entity.GroupMemberKey;
import com.iiex.cost_share_service.entity.User;
import java.util.List;


public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberKey> {
    boolean existsByGroupAndUser(Group group, User user);
}

