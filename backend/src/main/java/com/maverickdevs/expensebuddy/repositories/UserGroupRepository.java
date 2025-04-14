package com.maverickdevs.expensebuddy.repositories;

import com.maverickdevs.expensebuddy.entities.Group;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.entities.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {

    @Query("SELECT ug.user FROM UserGroup ug WHERE ug.group.groupId = :groupId")
    List<User> findUsersByGroupId(Integer groupId);

    @Query("SELECT ug FROM UserGroup ug WHERE ug.id.groupId = :groupId")
    List<UserGroup> findByGroupId(Integer groupId);

    @Query("SELECT ug.group FROM UserGroup ug WHERE ug.user.username = :userName")
    List<Group> findGroupsByUserName(@Param("userName") String userName);

}
