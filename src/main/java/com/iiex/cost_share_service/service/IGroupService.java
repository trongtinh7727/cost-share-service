package com.iiex.cost_share_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iiex.cost_share_service.entity.Group;
import com.iiex.cost_share_service.entity.GroupMember;

@Service
public interface IGroupService {
    public GroupMember addUserToGroupByEmail(Long groupId, String email);

    public void deleteGroup(Long groupId);

    public Group getGroupById(Long groupId);

    public List<Group> getGroupsByUser(Long userId);

    public Group createGroup(String groupName, Long userId);

    public List<Group> getAllGroups();
}
