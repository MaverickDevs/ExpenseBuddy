package com.maverickdevs.expensebuddy.services.impl;

import com.maverickdevs.expensebuddy.dto.request.GroupRequestDTO;
import com.maverickdevs.expensebuddy.dto.response.GroupResponseDTO;
import com.maverickdevs.expensebuddy.entities.Group;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.entities.UserGroup;
import com.maverickdevs.expensebuddy.entities.UserGroupId;
import com.maverickdevs.expensebuddy.repositories.GroupRepository;
import com.maverickdevs.expensebuddy.repositories.UserGroupRepository;
import com.maverickdevs.expensebuddy.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Data
@Service
public class GroupServiceImpl {
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository, UserGroupRepository userGroupRepository){
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;

    }
    public List<Group> getallgroups(){
        return groupRepository.findAll();
    }

    public Group createGroup(GroupRequestDTO groupRequestDTO) {
        Group group = new Group();
        group.setName(groupRequestDTO.getName());
        group.setCreatedAt(LocalDateTime.now());
        User creator = userRepository.findById(groupRequestDTO.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        group.setCreatedBy(creator);
        Group groupCreated = groupRepository.save(group);
        List<Integer> usersingroup = groupRequestDTO.getUserIds();
        for(Integer userId : usersingroup){
            User tempUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            UserGroup userGroup = new UserGroup(new UserGroupId(userId, groupCreated.getGroupId()), tempUser, groupCreated, LocalDateTime.now());
            userGroupRepository.save(userGroup);

        }
        return groupCreated;
    }

    public List<User> getUsersByGroupId(Integer groupId){
        List<User> users = userGroupRepository.findUsersByGroupId(groupId);
        return users;
    }

    public Page<GroupResponseDTO> getUserGroups(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastModifiedAt").descending());
        Page<Object[]> results = groupRepository.findGroupsWithDetails(userId, pageable);

        return results.map(row -> new GroupResponseDTO(
                (String) row[0],  // name
                ((BigDecimal) row[1]),  // owedAmount
                ((Number) row[2]).intValue(), // numberOfPeople
                (row[3] != null ? ((Timestamp) row[3]).toLocalDateTime() : null) // lastModifiedAt
        ));
    }

}
