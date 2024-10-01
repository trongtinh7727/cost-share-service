package com.iiex.cost_share_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iiex.cost_share_service.entity.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}

