package com.maverickdevs.expensebuddy.repositories;

import com.maverickdevs.expensebuddy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);

    public User findByEmail(String email);

    public List<User> findAll();

    //public List<User> findByNameContainingIgnoreCaseOrPhoneNumberContaining(String name, String phoneNumber);

    public List<User> findByusernameContainingIgnoreCase(String name);
}
