package com.maverickdevs.expensebuddy.services;

import com.maverickdevs.expensebuddy.entities.RefreshToken;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);

    RefreshToken verifyExpiration(RefreshToken token);

}
