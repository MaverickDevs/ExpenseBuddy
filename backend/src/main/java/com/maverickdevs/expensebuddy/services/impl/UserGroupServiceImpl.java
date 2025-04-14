package com.maverickdevs.expensebuddy.services.impl;

import com.maverickdevs.expensebuddy.dto.response.GroupResponseDTO;
import com.maverickdevs.expensebuddy.dto.response.UserDTO;
import com.maverickdevs.expensebuddy.entities.Group;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.repositories.UserGroupRepository;
import com.maverickdevs.expensebuddy.utils.ResponseUtils;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
@Service
public class UserGroupServiceImpl {

    private final UserGroupRepository userGroupRepository;

    public UserGroupServiceImpl(UserGroupRepository userGroupRepository){
        this.userGroupRepository = userGroupRepository;
    }


    public List<UserDTO> getUsersByGroupId(Integer groupId){
        List<User> users;
        users = userGroupRepository.findUsersByGroupId(groupId);
        List<UserDTO> userDtos = new java.util.ArrayList<>(List.of());

        for(User user : users){
            UserDTO userDTO = new UserDTO(user.getUsername(),user.getEmail());
            userDtos.add(userDTO);
        }
        return userDtos;
    }

    public ResponseEntity<?> getGroupsForUser(String username) {
        List<Group> groups = userGroupRepository.findGroupsByUserName(username);
        if(groups.isEmpty()){
            return ResponseUtils.createResponse("error", "No groups for this user ");
        }
        List<GroupResponseDTO> groupResponseDTOList = groups.stream()
                .map(group -> GroupResponseDTO.builder()
                        .users(userGroupRepository.findUsersByGroupId(group.getGroupId()))
                        .groupId(group.getGroupId())
                        .name(group.getName())
                        .lastModifiedAt(group.getLastModifiedAt())
                        .owedAmount(BigDecimal.valueOf(100))
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(groups);
    }
}
