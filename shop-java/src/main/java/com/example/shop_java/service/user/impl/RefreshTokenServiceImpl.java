package com.example.shop_java.service.user.impl;

import com.example.shop_java.entity.user.RefreshToken;
import com.example.shop_java.exception.EntityNotFoundException;
import com.example.shop_java.repository.RefreshTokenRepository;
import com.example.shop_java.service.user.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken findByToken(String token) {
        return this.refreshTokenRepository.findByToken(token).orElseThrow(() -> new EntityNotFoundException("Refresh token not found"));
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return this.refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        this.refreshTokenRepository.delete(refreshToken);
    }

}
