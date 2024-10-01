package com.iiex.cost_share_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiex.cost_share_service.entity.Group;
import com.iiex.cost_share_service.entity.GroupMember;
import com.iiex.cost_share_service.entity.User;
import com.iiex.cost_share_service.repository.GroupMemberRepository;
import com.iiex.cost_share_service.repository.GroupRepository;
import com.iiex.cost_share_service.repository.UserRepository;

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

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    public String addMemberToGroupByEmail(Long groupId, String email) {
        Optional<User> userOpt = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Group group = getGroupById(groupId);
            if (group != null) {
                GroupMember groupMember = new GroupMember();
                groupMember.setGroup(group);
                groupMember.setUser(user);
                groupMemberRepository.save(groupMember);
                return "User added to group.";
            } else {
                return "Group not found.";
            }
        }
        return "User with email " + email + " not found.";
    }
}
