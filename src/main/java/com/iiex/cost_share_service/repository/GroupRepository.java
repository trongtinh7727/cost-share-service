package com.iiex.cost_share_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iiex.cost_share_service.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByCreatedByUserId(Long userId);
}