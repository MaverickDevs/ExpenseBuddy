package com.maverickdevs.expensebuddy.controllers;

import com.maverickdevs.expensebuddy.dto.response.UserDTO;
import com.maverickdevs.expensebuddy.services.impl.UserGroupServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usergroups")
public class UserGroupController {

    private final UserGroupServiceImpl userGroupimpl;

    public UserGroupController(UserGroupServiceImpl userGroupimpl){
        this.userGroupimpl = userGroupimpl;
    }

    @GetMapping("/{groupId}/users")
    public ResponseEntity<?> getusersbygroup(@PathVariable("groupId") Integer groupId){
        List<UserDTO> userDTOSs = userGroupimpl.getUsersByGroupId(groupId);
        if (userDTOSs == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group not found");
        }

        if (userDTOSs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users in this group");
        }

        return ResponseEntity.ok(userDTOSs);
    }


}
