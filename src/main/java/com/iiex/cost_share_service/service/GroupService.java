package com.iiex.cost_share_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiex.cost_share_service.entity.Group;
import com.iiex.cost_share_service.entity.GroupMember;
import com.iiex.cost_share_service.entity.User;
import com.iiex.cost_share_service.repository.GroupMemberRepository;
import com.iiex.cost_share_service.repository.GroupRepository;
import com.iiex.cost_share_service.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group createGroup(String groupName, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = new Group();
        group.setGroupName(groupName);
        group.setCreatedBy(user);
        group.setCreatedAt(LocalDateTime.now());
        return groupRepository.save(group);
    }

    public List<Group> getGroupsByUser(Long userId) {
        return groupRepository.findByCreatedByUserId(userId);
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    public GroupMember addUserToGroupByEmail(Long groupId, String email) {
        // Tìm nhóm theo id
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // Tìm người dùng theo email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));

        // Kiểm tra nếu người dùng đã là thành viên của nhóm
        boolean isMemberAlready = groupMemberRepository.existsByGroupAndUser(group, user);
        if (isMemberAlready) {
            throw new RuntimeException("User is already a member of the group");
        }

        // Thêm người dùng vào nhóm
        GroupMember groupMember = new GroupMember();
        groupMember.setGroup(group);
        groupMember.setUser(user);
        groupMember.setJoinedAt(LocalDateTime.now());

        return groupMemberRepository.save(groupMember);
    }
}
