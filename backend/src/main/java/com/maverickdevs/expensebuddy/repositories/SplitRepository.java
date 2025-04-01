package com.maverickdevs.expensebuddy.repositories;

import com.maverickdevs.expensebuddy.entities.Split;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SplitRepository extends JpaRepository<Split, UUID> {
}
