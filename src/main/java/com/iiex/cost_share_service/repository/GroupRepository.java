package com.iiex.cost_share_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iiex.cost_share_service.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT g FROM Group g " +
            "LEFT JOIN GroupMember gm ON g.groupId = gm.group.groupId " +
            "WHERE g.createdBy.userId = :userId OR gm.user.userId = :userId")
    List<Group> findGroupsByUserId(@Param("userId") Long userId);
}