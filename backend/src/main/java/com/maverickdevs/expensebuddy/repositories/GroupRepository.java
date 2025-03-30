package com.maverickdevs.expensebuddy.repositories;

import com.maverickdevs.expensebuddy.entities.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {



    @Query(value = """
        SELECT g.name AS name, 
               COALESCE(SUM(e.amount), 0) AS owedAmount, 
               COUNT(ug.user_id) AS numberOfPeople, 
               g.last_modified_at AS lastModifiedAt 
        FROM groups g
        JOIN usergroups ug ON ug.group_id = g.id  
        LEFT JOIN expenses e ON e.group_id = g.id AND e.paid_by <> :userId
        WHERE ug.user_id = :userId  
        GROUP BY g.id, g.name, g.last_modified_at
        """,
            countQuery = """
        SELECT COUNT(DISTINCT g.id) 
        FROM groups g
        JOIN usergroups ug ON ug.group_id = g.id
        WHERE ug.user_id = :userId
        """,
            nativeQuery = true)
    Page<Object[]> findGroupsWithDetails(@Param("userId") Integer userId, Pageable pageable);
}
