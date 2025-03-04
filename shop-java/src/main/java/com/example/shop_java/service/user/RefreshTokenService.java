package com.example.shop_java.service.user;

import com.example.shop_java.entity.user.RefreshToken;

public interface RefreshTokenService {

    RefreshToken findByToken(String token);

    RefreshToken save(RefreshToken refreshToken);

    void delete(RefreshToken refreshToken);

}
