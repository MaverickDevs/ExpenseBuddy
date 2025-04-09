package com.maverickdevs.expensebuddy.repositories;

import com.maverickdevs.expensebuddy.entities.Expense;
import com.maverickdevs.expensebuddy.entities.Split;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SplitRepository extends JpaRepository<Split, UUID> {

    @Query("SELECT s FROM Split s WHERE s.expense = :expense")
    List<Split> findByExpense(@Param("expense") Expense expense);
}
