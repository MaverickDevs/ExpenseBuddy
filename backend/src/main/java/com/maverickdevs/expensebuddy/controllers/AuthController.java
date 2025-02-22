package com.maverickdevs.expensebuddy.controllers;

import com.maverickdevs.expensebuddy.dto.request.UserInfoDTO;
import com.maverickdevs.expensebuddy.dto.response.JwtResponseDTO;
import com.maverickdevs.expensebuddy.entities.RefreshToken;
import com.maverickdevs.expensebuddy.services.RefreshTokenService;
import com.maverickdevs.expensebuddy.services.impl.JwtServiceImpl;
import com.maverickdevs.expensebuddy.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final JwtServiceImpl jwtService;

    private final RefreshTokenService refreshTokenService;

    private final UserServiceImpl userService;

    @Autowired
    public AuthController(JwtServiceImpl jwtService, RefreshTokenService refreshTokenService, UserServiceImpl userService){
        this.jwtService = jwtService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("auth/v1/signup")
    public ResponseEntity SignUp(@RequestBody UserInfoDTO userInfoDto){
        try{
            Boolean isSignUped = userService.signupUser(userInfoDto);
            if(Boolean.FALSE.equals(isSignUped)){
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken = jwtService.GenerateToken(userInfoDto.getUsername());
            return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken).
                    token(refreshToken.getToken()).build(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
