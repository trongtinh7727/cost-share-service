package com.iiex.cost_share_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iiex.cost_share_service.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}