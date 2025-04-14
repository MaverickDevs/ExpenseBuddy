package com.maverickdevs.expensebuddy.services.impl;

import com.maverickdevs.expensebuddy.dto.request.GroupRequestDTO;
import com.maverickdevs.expensebuddy.dto.response.DebtResponseDTO;
import com.maverickdevs.expensebuddy.dto.response.GroupResponseDTO;
import com.maverickdevs.expensebuddy.entities.*;
import com.maverickdevs.expensebuddy.repositories.GroupRepository;
import com.maverickdevs.expensebuddy.repositories.SplitRepository;
import com.maverickdevs.expensebuddy.repositories.UserGroupRepository;
import com.maverickdevs.expensebuddy.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Data
@Service
public class GroupServiceImpl {
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final SplitRepository splitRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository, UserGroupRepository userGroupRepository, SplitRepository splitRepository){
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.splitRepository = splitRepository;

    }
    public List<Group> getallgroups(){
        return groupRepository.findAll();
    }

    @Transactional
    public Group updateGroup(Integer groupId, GroupRequestDTO groupUpdateDTO) {
        //This is returning group for now, can modify it to return response DTO later
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        // Update the name if provided
        if (groupUpdateDTO.getName() != null) {
            group.setName(groupUpdateDTO.getName());
        }

        // Update members if provided
        if (groupUpdateDTO.getUserIds() != null) {
            updateUserGroupRelations(group, groupUpdateDTO.getUserIds());
        }

        group.setLastModifiedAt(LocalDateTime.now());
        return groupRepository.save(group);
    }

    @Transactional
    public void updateUserGroupRelations(Group group, List<Integer> newUserIds) {
        List<UserGroup> existingUserGroups = userGroupRepository.findByGroupId(group.getGroupId());
        Set<Integer> existingUserIds = existingUserGroups.stream()
                .map(ug -> ug.getUser().getId())
                .collect(Collectors.toSet());

        // Remove users who are no longer in the group
        for (UserGroup userGroup : existingUserGroups) {
            if (!newUserIds.contains(userGroup.getUser().getId())) {
                userGroupRepository.delete(userGroup);
            }
        }

        // Add new users
        for (Integer userId : newUserIds) {
            if (!existingUserIds.contains(userId)) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
                UserGroup newUserGroup = new UserGroup(
                        new UserGroupId(userId, group.getGroupId()),
                        user,
                        group,
                        LocalDateTime.now()
                );
                userGroupRepository.save(newUserGroup);
            }
        }
    }

    public GroupResponseDTO createGroup(GroupRequestDTO groupRequestDTO) {
        Group group = new Group();
        group.setName(groupRequestDTO.getName());
        group.setCreatedAt(LocalDateTime.now());
        User creator = userRepository.findById(groupRequestDTO.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        group.setCreatedBy(creator);
        Group groupCreated = groupRepository.save(group);
        List<Integer> usersingroup = groupRequestDTO.getUserIds();
        List<User> userList = new java.util.ArrayList<>(List.of());
        for(Integer userId : usersingroup){
            User tempUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            userList.add(tempUser);
            UserGroup userGroup = new UserGroup(new UserGroupId(userId, groupCreated.getGroupId()), tempUser, groupCreated, LocalDateTime.now());
            userGroupRepository.save(userGroup);

        }

        GroupResponseDTO groupResponseDTO = GroupResponseDTO.builder()
                .groupId(groupCreated.getGroupId())
                .lastModifiedAt(groupCreated.getLastModifiedAt())
                .name(groupCreated.getName())
                .users(userList)
                .owedAmount(BigDecimal.valueOf(0))
                .build();
        return groupResponseDTO;
    }

    public List<User> getUsersByGroupId(Integer groupId){
        return userGroupRepository.findUsersByGroupId(groupId);
    }


    public List<DebtResponseDTO> getGroupDebts(Integer groupId) {
        User currentuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByUsername(currentuser.getUsername());

        List<Split> splits = splitRepository.findSplitsInGroupInvolvingUser(groupId, user.getId());

        List<Integer> userIds = splits.stream().flatMap(
                split-> Stream.of(split.getDebtor().getId(), split.getCreditor().getId()))
                .distinct().toList();

        Map<Integer, String> userNames = userRepository.findAllById(userIds).stream().collect(Collectors.toMap(User::getId, User::getUsername));

        Map<Integer, BigDecimal> netBalances = new HashMap<>();

        for(Split split:splits){
            Integer debtor = split.getDebtor().getId();
            Integer creditor = split.getCreditor().getId();
            BigDecimal amount = split.getSplitAmount();

            if(debtor.equals(user.getId())){
                netBalances.merge(creditor, amount, BigDecimal::add);
            }
            if(creditor.equals(user.getId())){
                netBalances.merge(debtor, amount.negate(), BigDecimal::add);
            }
        }

        List<DebtResponseDTO> ans =  netBalances.entrySet().stream().map(entry -> {
            DebtResponseDTO res = new DebtResponseDTO();
            res.setDebt(entry.getValue());
            res.setDebtorName(userNames.get(entry.getKey()));
            res.setDebtorId(entry.getKey());

            return res;
        }).toList();

        return ans;
    }
}
