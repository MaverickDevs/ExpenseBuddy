package com.maverickdevs.expensebuddy.controllers;

import com.maverickdevs.expensebuddy.repositories.SplitRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/splits")
public class SplitController {

    private final SplitRepository splitRepository;

    public SplitController(SplitRepository splitRepository){
        this.splitRepository = splitRepository;
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getall(){
        return ResponseEntity.ok(splitRepository.findAll());
    }
}
