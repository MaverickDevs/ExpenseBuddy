package com.maverickdevs.expensebuddy.controllers;

import com.maverickdevs.expensebuddy.dto.request.GroupRequestDTO;
import com.maverickdevs.expensebuddy.dto.response.GroupResponseDTO;
import com.maverickdevs.expensebuddy.entities.Expense;
import com.maverickdevs.expensebuddy.entities.Group;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.services.impl.GroupServiceImpl;
import com.maverickdevs.expensebuddy.services.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupServiceImpl groupService;

    @Autowired
    public GroupController(GroupServiceImpl groupService){
        this.groupService = groupService;
    }

    @GetMapping("/getall")
    public List<Group> getallgroups(){
        return groupService.getallgroups();
    }
    @PostMapping("/create")
    public ResponseEntity<?> addgroup(@RequestBody GroupRequestDTO groupRequestDTO){
        Group newGroup = groupService.createGroup(groupRequestDTO);
        if (newGroup == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot create group");
        }
        return ResponseEntity.ok(newGroup);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Group> updateGroup(@PathVariable Integer groupId, @RequestBody GroupRequestDTO groupUpdateDTO) {
        Group updatedGroup = groupService.updateGroup(groupId, groupUpdateDTO);
        return ResponseEntity.ok(updatedGroup);
    }

    @GetMapping("/{userId}/groups")
    public ResponseEntity<?> getgroupsforuser(@PathVariable("userId") Integer userId,@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size){
        //do nothing

        Page<GroupResponseDTO> groupResponseDTOS = groupService.getUserGroups(userId,page,size);
        if(groupResponseDTOS == null){
            throw new EntityNotFoundException("No groups found for user ID: " + userId);
        }
        return ResponseEntity.ok(groupResponseDTOS);
    }


}
