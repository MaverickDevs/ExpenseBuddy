package com.maverickdevs.expensebuddy.services.impl;

import com.maverickdevs.expensebuddy.dto.response.UserDTO;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.repositories.UserGroupRepository;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
