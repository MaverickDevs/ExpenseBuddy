package com.maverickdevs.expensebuddy.repositories;

import com.maverickdevs.expensebuddy.entities.Group;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.entities.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {

    @Query("SELECT ug.user FROM UserGroup ug WHERE ug.group.groupId = :groupId")
    List<User> findUsersByGroupId(Integer groupId);

    @Query("SELECT COUNT(ug.userId) FROM UserGroup ug WHERE ug.groupId = :groupId")
    int getUserCountByGroupId(Integer groupId);

}
