package com.iiex.cost_share_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iiex.cost_share_service.dto.response.ResponseVO;
import com.iiex.cost_share_service.entity.Group;
import com.iiex.cost_share_service.entity.GroupMember;
import com.iiex.cost_share_service.service.IGroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private IGroupService groupService;

    // Add a user to a group by email
    @PostMapping("/{groupId}/add-member")
    public ResponseEntity<ResponseVO<GroupMember>> addUserToGroupByEmail(@PathVariable Long groupId,
            @RequestParam String email) {
        GroupMember data = groupService.addUserToGroupByEmail(groupId, email);
        ResponseVO<GroupMember> response = ResponseVO.success(
                "User with email " + email + " added to the group.", data);
        return ResponseEntity.ok(response);
    }

    // Create a group
    @PostMapping
    public ResponseEntity<ResponseVO<Group>> createGroup(@RequestParam String name, @RequestParam Long userId) {
        Group data = groupService.createGroup(name, userId);
        ResponseVO<Group> response = ResponseVO.success("Group created successfully", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get groups of a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseVO<List<Group>>> getGroupsByUser(@PathVariable Long userId) {
        List<Group> data = groupService.getGroupsByUser(userId);
        ResponseVO<List<Group>> response = ResponseVO.success("User's groups retrieved successfully", data);
        return ResponseEntity.ok(response);
    }

    // Delete a group
    @DeleteMapping("/{groupId}")
    public ResponseEntity<ResponseVO<Void>> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        ResponseVO<Void> response = ResponseVO.success("Group deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
