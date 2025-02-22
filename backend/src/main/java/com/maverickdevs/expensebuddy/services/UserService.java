package com.maverickdevs.expensebuddy.services;

import com.maverickdevs.expensebuddy.dto.request.UserInfoDTO;
import com.maverickdevs.expensebuddy.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails loadUserByUsername(String username);
    User checkIfUserAlreadyExists(UserInfoDTO userInfoDto);
    Boolean signupUser(UserInfoDTO userInfoDTO);

}
