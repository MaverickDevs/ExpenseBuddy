package com.maverickdevs.expensebuddy.services.impl;

import com.maverickdevs.expensebuddy.dto.request.UserInfoDTO;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;


@Component
@Data
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Could not find User!!");
        }
        return new CustomUserDetails(user);
    }

    public User checkIfUserAlreadyExists(UserInfoDTO userInfoDto) {
        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signupUser(UserInfoDTO userInfoDTO) {

        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoDTO))){
            return false;
        }
        Integer userId = Integer.parseInt(UUID.randomUUID().toString());
        User newUser = new User(userId, userInfoDTO.getUsername(),userInfoDTO.getEmail(),userInfoDTO.getPassword(), LocalDateTime.now());
        userRepository.save(newUser);
        return true;
    }
}
