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
@Table(name = "usergroups")
public class UserGroup {

    @EmbeddedId
    private UserGroupId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    private LocalDateTime joinedAt;

    // Getters and Setters
}
