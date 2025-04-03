package com.maverickdevs.expensebuddy.repositories;

import com.maverickdevs.expensebuddy.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    @Query("SELECT e FROM Expense e WHERE e.group.id = :groupId ORDER BY e.createdAt DESC")
    Page<Expense> findByGroupId(Integer groupId, PageRequest createdAt);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.group.id = :groupId AND e.paidBy != :userId")
    BigDecimal getOwedAmountByGroupId(@Param("groupId") Integer groupId, @Param("userId") Integer userId);

}
