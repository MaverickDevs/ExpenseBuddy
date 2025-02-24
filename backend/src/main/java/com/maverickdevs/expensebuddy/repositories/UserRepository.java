package com.maverickdevs.expensebuddy.repositories;

import com.maverickdevs.expensebuddy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);

    public User findByEmail(String email);
}
