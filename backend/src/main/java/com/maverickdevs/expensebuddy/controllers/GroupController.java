package com.maverickdevs.expensebuddy.controllers;

import com.maverickdevs.expensebuddy.dto.request.GroupRequestDTO;
import com.maverickdevs.expensebuddy.dto.response.DebtResponseDTO;
import com.maverickdevs.expensebuddy.dto.response.GroupResponseDTO;
import com.maverickdevs.expensebuddy.entities.Group;
import com.maverickdevs.expensebuddy.services.impl.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/{groupId}/group-debts")
    public ResponseEntity<?> getGroupDebts(@PathVariable Integer groupId){
        List<DebtResponseDTO> debts = groupService.getGroupDebts(groupId);
        return ResponseEntity.ok(debts);
    }

    @PostMapping("/create")
    public ResponseEntity<?> addgroup(@RequestBody GroupRequestDTO groupRequestDTO){
        GroupResponseDTO groupResponseDTO = groupService.createGroup(groupRequestDTO);
        if (groupResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot create group");
        }
        return ResponseEntity.ok(groupResponseDTO);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Group> updateGroup(@PathVariable Integer groupId, @RequestBody GroupRequestDTO groupUpdateDTO) {
        Group updatedGroup = groupService.updateGroup(groupId, groupUpdateDTO);
        return ResponseEntity.ok(updatedGroup);
    }

    //get groups using userid

}
