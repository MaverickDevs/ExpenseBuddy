package com.maverickdevs.expensebuddy.controllers;

import com.maverickdevs.expensebuddy.dto.response.UserDTO;
import com.maverickdevs.expensebuddy.services.impl.JwtServiceImpl;
import com.maverickdevs.expensebuddy.services.impl.UserGroupServiceImpl;
import com.maverickdevs.expensebuddy.utils.ResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usergroups")
public class UserGroupController {

    private final UserGroupServiceImpl userGroupimpl;

    private final JwtServiceImpl jwtService;

    public UserGroupController(UserGroupServiceImpl userGroupimpl, JwtServiceImpl jwtService){

        this.userGroupimpl = userGroupimpl;
        this.jwtService = jwtService;
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



    @GetMapping("/getgroupsforuser")
    public ResponseEntity<?> getGroupsForUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);
        if(username == null || username.isEmpty()){
            ResponseUtils.createResponse("error","No such user exists");
        }
        return ResponseEntity.ok(userGroupimpl.getGroupsForUser(username));
    }


}
