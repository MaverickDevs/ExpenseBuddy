package com.maverickdevs.expensebuddy.services.impl;

import com.maverickdevs.expensebuddy.entities.RefreshToken;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.repositories.RefreshTokenRepository;
import com.maverickdevs.expensebuddy.repositories.UserRepository;
import com.maverickdevs.expensebuddy.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository){
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RefreshToken createRefreshToken(String username) {
        User userInfoExtracted = userRepository.findByUsername(username);


        RefreshToken refreshToken = RefreshToken.builder()
                .user(userInfoExtracted)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(Instant.now())<0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken()+" Refresh token is expired. Please login again!");
        }
        return token;
    }
}
