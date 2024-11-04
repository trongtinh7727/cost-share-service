package com.iiex.cost_share_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iiex.cost_share_service.dto.response.ApiResponse;
import com.iiex.cost_share_service.entity.Group;
import com.iiex.cost_share_service.entity.GroupMember;
import com.iiex.cost_share_service.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    // Thêm người dùng vào nhóm bằng email
    @PostMapping("/{groupId}/add-member")
    public ResponseEntity<ApiResponse<GroupMember>> addUserToGroupByEmail(@PathVariable Long groupId,
            @RequestParam String email) {
        try {
            GroupMember data = groupService.addUserToGroupByEmail(groupId, email);
            ApiResponse<GroupMember> response = new ApiResponse<>(200,
                    "User with email " + email + " added to the group.", data);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<GroupMember> response = new ApiResponse<>(400, ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Tạo nhóm
    @PostMapping
    public ResponseEntity<ApiResponse<Group>> createGroup(@RequestParam String name, @RequestParam Long userId) {
        Group data = groupService.createGroup(name, userId);
        ApiResponse<Group> response = new ApiResponse<>(200, "Group created successfully", data);
        return ResponseEntity.ok(response);
    }

    // Lấy danh sách nhóm của người dùng
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Group>>> getGroupsByUser(@PathVariable Long userId) {
        List<Group> data = groupService.getGroupsByUser(userId);
        ApiResponse<List<Group>> response = new ApiResponse<>(200, "User's groups retrieved successfully", data);
        return ResponseEntity.ok(response);
    }

    // Xóa nhóm
    @DeleteMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Void>> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        ApiResponse<Void> response = new ApiResponse<>(200, "Group deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}

