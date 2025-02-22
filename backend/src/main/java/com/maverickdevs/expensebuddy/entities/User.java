package com.maverickdevs.expensebuddy.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private LocalDateTime createdAt;
//
//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = LocalDateTime.now();
//    }
}


